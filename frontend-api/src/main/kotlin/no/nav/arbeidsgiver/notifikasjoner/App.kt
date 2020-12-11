package no.nav.arbeidsgiver.notifikasjoner

import no.nav.arbeidsgiver.notifikasjoner.kafkastrøm.NotifikasjonsstrømKafka
import no.nav.arbeidsgiver.notifikasjoner.persistence.Notifikasjonslager
import no.nav.arbeidsgiver.notifikasjoner.persistence.NotifikasjonslagerDatabase
import org.slf4j.LoggerFactory

fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) {
    LoggerFactory.getLogger("main").info("env: {}", System.getenv().keys.joinToString(", "))
    startServer(
        lagNotifikasjonsstrøm = ::NotifikasjonsstrømKafka,
        lagNotifikasjonslager = ::lagNotifikasjonslager
    )
}

fun lagNotifikasjonslager(): Notifikasjonslager {
    val prefix = "DB_NOTIFIKASJONER"
    fun lookup(navn: String): String {
        val value = System.getenv("${prefix}_$navn")
        return if (value == null || value.isBlank())
            throw RuntimeException("Finner ikke env-variabel $navn")
         else
            value
    }

    val host = lookup("HOST")
    val port = lookup("PORT")
    val database = lookup("DATABASE")
    val jdbcUrl = "jdbc:postgresql://$host:$port/$database"

    return NotifikasjonslagerDatabase(
        jdbcUrl = jdbcUrl,
        username = lookup("USERNAME"),
        password = lookup("PASSWORD")
    )
}

