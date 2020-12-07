package no.nav.arbeidsgiver.notifikasjon.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.lang.RuntimeException
import javax.sql.DataSource

fun getDataSource(env: Map<String, String>): DataSource {
    fun lookup(navn: String): String =
        env["NAIS_DATABASE_NOTIFIKASJON_FRONTEND_API_NOTIFIKASJONER_$navn"]
            ?: throw RuntimeException("Finner ikke env-variabel $navn");

    val config = HikariConfig().apply {
        jdbcUrl =  "jdbc:postgresql://${lookup("HOST")}:${lookup("PORT")}/${lookup("DATABASE")}"
        username = lookup("USERNAME")
        password = lookup("PASSWORD")
        maximumPoolSize = 3
        minimumIdle = 1
        idleTimeout = 10_001
        connectionTimeout = 1_000
        maxLifetime = 30_001
        driverClassName = "org.postgresql.Driver"
        initializationFailTimeout = 60_000 /* ms */
    }

    return HikariDataSource(config)
}
