package no.nav.arbeidsgiver.notifikasjoner.felles

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

private fun Boolean.asHttpStatusCode() =
    if (this)
        HttpStatusCode.OK
    else
        HttpStatusCode.InternalServerError

fun healthEndpoints(
    liveness: () -> Boolean,
    readiness: () -> Boolean
) = fun Routing.() {
        get("/liveness") {
            call.respond(liveness().asHttpStatusCode())
        }
        get("/readiness") {
            call.respond(readiness().asHttpStatusCode())
        }
    }
