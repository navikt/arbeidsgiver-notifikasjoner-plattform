package no.nav.arbeidsgiver.notifikasjoner

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import no.nav.arbeidsgiver.notifikasjoner.http_endpoint.root
import no.nav.arbeidsgiver.notifikasjoner.kafkastrøm.Notifikasjonsstrøm
import no.nav.arbeidsgiver.notifikasjoner.persistence.Notifikasjonslager
import no.nav.arbeidsgiver.notifikasjoner.service.kafkaToDatabaseService
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("main")!!

private suspend fun <T> Deferred<T>.isAlive(): Boolean {
    if (isActive) {
        return true
    }

    if (!isCompleted) {
        return false
    }

    return try {
        await()
        true
    } catch (e: Throwable) {
        false
    }
}

private suspend fun <T> Deferred<T>.isReady(): Boolean {
    if (!isCompleted) {
        return false
    }
    return try {
        await()
        true
    } catch (e: Throwable) {
        false
    }
}

fun startServer(
    lagNotifikasjonsstrøm:  suspend () -> Notifikasjonsstrøm,
    lagNotifikasjonslager: suspend () -> Notifikasjonslager
) = try {
    runBlocking(Dispatchers.IO) {
        log.info("main startet")

        val lager = async { lagNotifikasjonslager() }
        val strøm = async { lagNotifikasjonsstrøm() }

        launch {
            kafkaToDatabaseService(
                notifikasjonslager = lager.await(),
                notifikasjonsstrøm = strøm.await()
            )
        }

        suspend fun isReady(): Boolean =
            lager.isReady() && strøm.isReady()

        suspend fun isAlive(): Boolean =
            lager.isAlive() && strøm.isAlive()

        log.info("steg: embedded server")
        embeddedServer(
            Netty,
            port = 8080,
            module = root(
                isReady = ::isReady,
                isAlive = ::isAlive,
                notifikasjonslager = lager
            )
        ).start()

        log.info("All initialisering ferdig")
    }
} catch (e: Exception) {
    log.error("uhåndtert exception", e)
}

