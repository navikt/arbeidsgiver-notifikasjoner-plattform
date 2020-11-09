import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import no.nav.arbeidsgiver.notifikasjon.config.DataSourceBuilder
import org.slf4j.LoggerFactory
import java.time.Duration
import kotlin.concurrent.thread

val konsument = lagKafkaConsumer();

fun lesFraKafka() {
    val logger = LoggerFactory.getLogger("kafka-leser")!!
    konsument.subscribe(listOf("arbeidsgiver.arbeidsgiver-notifikasjon"))
    while (true) {
        val notifikasjoner = konsument.poll(Duration.ofMillis(1000));
        notifikasjoner.forEach {
            logger.info("mottatt beskjed: {} ", it.value())
        }
    }
}

fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    val logger = LoggerFactory.getLogger("main")!!
    try {
        val config = DataSourceBuilder(System.getenv())
        config.migrate()
        thread(start = true) {
            lesFraKafka();
        }
        embeddedServer(
                Netty,
                port = 8080,
                module = Application::health
        ).start(wait = true)
    } catch (e: Exception) {
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
