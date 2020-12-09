package no.nav.arbeidsgiver.notifikasjon

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.*
import no.nav.arbeidsgiver.notifikasjon.config.getDataSource
import no.nav.arbeidsgiver.notifikasjon.config.getKafkaConsumer
import no.nav.arbeidsgiver.notifikasjon.http_endpoint.root
import no.nav.arbeidsgiver.notifikasjon.service.kafkaToDatabaseService
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import javax.sql.DataSource

private val log = LoggerFactory.getLogger("main")!!

fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) =
    runBlocking(Dispatchers.IO) {
        try {
            log.info("main startet")

            log.info("steg: kafka consumer ")
            val kafkaConsumer = async {
                getKafkaConsumer()
            }

            log.info("steg: data source")
            val dataSource = async {
                getDataSource(System.getenv())
                    .also(::migrateDatabase)
            }

            val kafkaToDatabaseJob = launch {
                kafkaToDatabaseService(kafkaConsumer.await(), dataSource.await())
            }

            fun isReady(): Boolean =
                kafkaConsumer.isCompleted
                        && dataSource.isCompleted
                        && kafkaToDatabaseJob.isActive

            fun isAlive(): Boolean =
                kafkaToDatabaseJob.isActive
                /* sjekke at database, kafka, webserver kjører */

            log.info("steg: embedded server")
            embeddedServer(
                Netty,
                port = 8080,
                module = root(
                    isReady = ::isReady,
                    isAlive = ::isAlive,
                    deferredDataSource = dataSource
                )
            ).start()

            log.info("All initialisering ferdig")
        } catch (e: Exception) {
            log.error("uhåndtert exception", e)
        }
    }

private fun migrateDatabase(dataSource: DataSource) =
    Flyway.configure()
        .dataSource(dataSource)
        .load()
        .migrate()

