import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import PRODUCER.send

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8080,
        module = Application::health
    ).start(wait = true)
}

fun Application.health() {
    routing {
        get("/liveness") {
            call.respond(HttpStatusCode.OK)
        }

        get("/readiness") {
            call.respond(HttpStatusCode.OK)
        }

        get("/hello") {
            send("HEISANN KAFKA!")
            call.respond(
                HttpStatusCode.OK,
                "Melding puttet på kafka"
            )
        }
    }
}

