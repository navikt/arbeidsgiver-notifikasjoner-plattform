package no.nav.arbeidsgiver.notifikasjoner.http_endpoint

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.coroutines.Deferred
import no.nav.arbeidsgiver.notifikasjoner.persistence.Notifikasjonslager

private fun Boolean.asHttpStatusCode(): HttpStatusCode =
    if (this) HttpStatusCode.OK else HttpStatusCode.InternalServerError

fun root(
    isReady: suspend () -> Boolean,
    isAlive: suspend () -> Boolean,
    notifikasjonslager: Deferred<Notifikasjonslager>
): Application.() -> Unit =
    fun Application.() {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }

        routing {
            get("/liveness") {
                call.respond(isAlive().asHttpStatusCode())
            }

            get("/readiness") {
                call.respond(isReady().asHttpStatusCode())
            }

            get("/notifikasjoner") get@{
                if (!notifikasjonslager.isCompleted) {
                    call.respond(HttpStatusCode.ServiceUnavailable)
                    return@get
                }
                val notifikasjoner = notifikasjonslager
                    .await()
                    .hentNotifikasjoner("44444444")
                call.respond(notifikasjoner)
            }
        }
    }



