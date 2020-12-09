package no.nav.arbeidsgiver.notifikasjon.http_endpoint

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.coroutines.Deferred
import no.nav.arbeidsgiver.notifikasjon.finnNotifikasjon
import javax.sql.DataSource

private val dataSourceKey = AttributeKey<DataSource>("dataSource")

private fun Boolean.asHttpStatusCode(): HttpStatusCode =
    if (this) HttpStatusCode.OK else HttpStatusCode.InternalServerError

fun root(
    isReady: suspend () -> Boolean,
    isAlive: suspend () -> Boolean,
    deferredDataSource: Deferred<DataSource>
): Application.() -> Unit =
    fun Application.() {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)

                // Configure Jackson's ObjectMapper here
            }
        }
        routing {
            get("/liveness") {
                call.respond(isAlive().asHttpStatusCode())
            }

            get("/readiness") {
                call.respond(isReady().asHttpStatusCode())
            }

            get("/notifikasjoner") {
                if (!deferredDataSource.isCompleted) {
                    call.respond(HttpStatusCode.ServiceUnavailable)
                } else {
                    val dataSource = deferredDataSource.await()
                    val notifikasjoner = dataSource.connection.finnNotifikasjon("44444444")
                    call.respond(notifikasjoner.toString())
                }
            }
        }
    }



