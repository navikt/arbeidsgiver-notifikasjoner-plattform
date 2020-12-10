package no.nav.arbeidsgiver.notifikasjoner

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import no.nav.arbeidsgiver.notifikasjoner.http_endpoint.root
import no.nav.arbeidsgiver.notifikasjoner.persistence.Notifikasjonslager
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger("main")!!

fun startServer(
    lagNotifikasjonslager: suspend () -> Notifikasjonslager
) = try {
    runBlocking(Dispatchers.IO) {
        log.info("main startet")

        val lager = async { lagNotifikasjonslager() }

        fun isReady(): Boolean = lager.isCompleted

        fun isAlive(): Boolean = true /* sjekke at database, kafka, webserver kjører */

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

