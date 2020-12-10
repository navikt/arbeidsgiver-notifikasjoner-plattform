package no.nav.arbeidsgiver.notifikasjoner

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
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

        val strøm = async { lagNotifikasjonsstrøm() }
        val lager = async { lagNotifikasjonslager() }

        val kafkaToDatabaseJob = launch {
            kafkaToDatabaseService(
                notifikasjonslager = lager.await(),
                notifikasjonsstrøm = strøm.await()
            )
        }

        suspend fun isReady(): Boolean =
            strøm.isReady() && lager.isReady() && kafkaToDatabaseJob.isActive

        suspend fun isAlive(): Boolean =
            strøm.isAlive()  && lager.isAlive() && kafkaToDatabaseJob.isActive

        log.info("steg: embedded server")
        embeddedServer(
            Netty,
            port = 8080,
            module = healthEndpoints(
                isReady = ::isReady,
                isAlive = ::isAlive,
            )
        ).start()

        log.info("All initialisering ferdig")
    }
} catch (e: Exception) {
    log.error("uhåndtert exception", e)
}

fun healthEndpoints(isReady: suspend () -> Boolean, isAlive: suspend () -> Boolean)  =
    fun Application.() {
        routing {
            get("/liveness") {
                call.respond(if (isAlive()) HttpStatusCode.OK else HttpStatusCode.InternalServerError)
            }
            get("/readiness") {
                call.respond(if (isReady()) HttpStatusCode.OK else HttpStatusCode.InternalServerError)
            }
        }
    }