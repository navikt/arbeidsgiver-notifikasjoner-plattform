package no.nav.arbeidsgiver.notifikasjon

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.arbeidsgiver.notifikasjon.config.getKafkaConsumer
import no.nav.arbeidsgiver.notifikasjon.config.getDataSource
import no.nav.arbeidsgiver.notifikasjon.service.kafkaToDatabaseService
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import javax.sql.DataSource
import kotlin.concurrent.thread

val logger = LoggerFactory.getLogger("main")!!

fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    try {
        logger.info("main startet")

        logger.info("steg: embedded server")
        embeddedServer(
            Netty,
            port = 8080,
            module = Application::health
        ).start()


        logger.info("steg: kafka consumer ")
        val kafkaConsumer = getKafkaConsumer()

        logger.info("steg: data source")
        val dataSource = getDataSource(System.getenv())

        logger.info("steg: migrer data")
        migrateDatabase(dataSource)

        logger.info("steg: start kafka-til-database service")
        thread(start = true) { kafkaToDatabaseService(kafkaConsumer, dataSource) }

        logger.info("All initialisering ferdig")
    } catch (e: Exception) {
        logger.error("uhåndtert exception: ", e)
    }
}

private fun migrateDatabase(dataSource: DataSource) =
    Flyway.configure()
        .dataSource(dataSource)
        .load()
        .migrate()

fun Application.health() {
    routing {
        get("/liveness") {
            call.respond(HttpStatusCode.OK)
        }

        get("/readiness") {
            call.respond(HttpStatusCode.OK)
        }

        get("/hello") {
        }
    }
}
