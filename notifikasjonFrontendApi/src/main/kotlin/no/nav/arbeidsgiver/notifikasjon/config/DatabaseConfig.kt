package no.nav.arbeidsgiver.notifikasjon.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import javax.sql.DataSource


// Understands how to create a data source from environment variables
public class DataSourceBuilder(env: Map<String, String>) {

    private val hikariConfig = HikariConfig().apply {
        jdbcUrl = env["NAIS_DATABASE_NOTIFIKASJON_FRONTEND_API_NOTIFIKASJONER_URL"]
        maximumPoolSize = 3
        minimumIdle = 1
        idleTimeout = 10001
        connectionTimeout = 1000
        maxLifetime = 30001
    }

    fun getDataSource(): DataSource {
      return HikariDataSource(hikariConfig)
    }

    public fun migrate() =
            Flyway.configure()
                    .dataSource(getDataSource())
                    .load()
                    .migrate()

}
