package no.nav.arbeidsgiver.notifikasjon.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import java.lang.RuntimeException
import javax.sql.DataSource


// Understands how to create a data source from environment variables
class DataSourceBuilder(val env: Map<String, String>) {

    private fun hentEnvVariabel(variabel: String): String =
            env["NAIS_DATABASE_NOTIFIKASJON_FRONTEND_API_NOTIFIKASJONER_${variabel}"]
            ?: throw RuntimeException("Finner ikke env-variabel");

    val url = "jdbc:postgres://${hentEnvVariabel("HOST")}:${hentEnvVariabel("PORT")}/${hentEnvVariabel("DATABASE")}"

    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = url
        username = hentEnvVariabel("USERNAME")
        password = hentEnvVariabel("PASSWORD")
        maximumPoolSize = 3
        minimumIdle = 1
        idleTimeout = 10001
        connectionTimeout = 1000
        maxLifetime = 30001
    }

    fun getDataSource(): DataSource {
      return HikariDataSource(hikariConfig)
    }

    fun migrate() =
            Flyway.configure()
                    .dataSource(getDataSource())
                    .load()
                    .migrate()

}
