import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.arbeidsgiver.notifikasjon.config.DataSourceBuilder
import org.slf4j.LoggerFactory


fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger("main")!!
    try {
        val config = DataSourceBuilder(System.getenv())
        config.migrate()
        embeddedServer(
                Netty,
                port = 8080,
                module = Application::health
        ).start(wait = true)
    }
    catch (e: Exception) {
        logger.error("uhåndtert exception", e)
    }
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
        }
    }
}
