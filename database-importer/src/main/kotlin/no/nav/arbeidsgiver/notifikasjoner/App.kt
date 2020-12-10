package no.nav.arbeidsgiver.notifikasjoner

import no.nav.arbeidsgiver.notifikasjoner.kafkastrøm.NotifikasjonsstrømKafka
import no.nav.arbeidsgiver.notifikasjoner.persistence.Notifikasjonslager
import no.nav.arbeidsgiver.notifikasjoner.persistence.NotifikasjonslagerDatabase
import org.slf4j.LoggerFactory

fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    LoggerFactory.getLogger("main").info("env: {}", System.getenv().keys.joinToString(", "))
    startServer(
        lagNotifikasjonslager = ::lagNotifikasjonslager,
        lagNotifikasjonsstrøm = ::NotifikasjonsstrømKafka
    )
}

fun lagNotifikasjonslager(): Notifikasjonslager {
    val prefix = "DB_NOTIFIKASJONER"
    fun lookup(navn: String): String =
        System.getenv("${prefix}_$navn")
            ?: throw RuntimeException("Finner ikke env-variabel $navn")
    return NotifikasjonslagerDatabase(
        jdbcUrl = "jdbc:postgresql://${lookup("HOST")}:${lookup("PORT")}/${lookup("DATABASE")}",
        username = lookup("USERNAME"),
        password = lookup("PASSWORD")
    )
}

