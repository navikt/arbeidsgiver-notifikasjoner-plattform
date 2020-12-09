package no.nav.arbeidsgiver.notifikasjon.http_endpoint

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.coroutines.Deferred
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
        routing {
            get("/liveness") {
                call.respond(isAlive().asHttpStatusCode())
            }

            get("/readiness") {
                call.respond(isReady().asHttpStatusCode())
            }

            get("/notifikasjoner") {
                if (!deferredDataSource.isCancelled) {
                    call.respond(HttpStatusCode.ServiceUnavailable)
                } else {
                    val dataSource = deferredDataSource.await()
                    call.respond(deferredDataSource.isCompleted.asHttpStatusCode())
                }
            }
        }
    }



