package no.nav.arbeidsgiver.notifikasjon

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import no.nav.arbeidsgiver.config.getKafkaConsumer
import no.nav.arbeidsgiver.notifikasjon.config.getDataSource
import no.nav.arbeidsgiver.notifikasjon.service.kafkaToDatabaseService
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import javax.sql.DataSource
import kotlin.concurrent.thread

private var serviceAlive = true
private var serviceReady = false

suspend fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    val logger = LoggerFactory.getLogger("main")!!
    try {
        val kafkaConsumerJob = GlobalScope.async { getKafkaConsumer() }

        val dataSourceJob = GlobalScope.async {
            getDataSource(System.getenv())
                .also { migrateDatabase(it) }
        }

        val kafkaConsumer = kafkaConsumerJob.await()
        val dataSource = dataSourceJob.await()

        serviceReady = true

        thread(start = true) { kafkaToDatabaseService(kafkaConsumer, dataSource) }

        embeddedServer(
            Netty,
            port = 8080,
            module = Application::health
        ).start(wait = true)
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
            call.respond(if (serviceAlive) HttpStatusCode.OK else HttpStatusCode.InternalServerError )
        }

        get("/readiness") {
            call.respond(if (serviceReady) HttpStatusCode.OK else HttpStatusCode.InternalServerError )
        }

        get("/hello") {
        }
    }
}