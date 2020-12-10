package no.nav.arbeidsgiver.notifikasjoner.persistence

import com.zaxxer.hikari.HikariDataSource
import no.nav.arbeidsgiver.notifikasjoner.model.Notifikasjon
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import javax.sql.DataSource

private val log = LoggerFactory.getLogger("DatabaseOperasjoner")

class NotifikasjonslagerDatabase(
    jdbcUrl: String,
    username: String,
    password: String
) : Notifikasjonslager {
    private val dataSource: DataSource = HikariDataSource().apply {
        this.jdbcUrl = jdbcUrl
        this.username = username
        this.password = password
        maximumPoolSize = 3
        minimumIdle = 1
        idleTimeout = 10_001
        connectionTimeout = 1_000
        maxLifetime = 30_001
        driverClassName = "org.postgresql.Driver"
        initializationFailTimeout = 60_000 /* ms */
    }

    init {
        Flyway.configure()
            .dataSource(dataSource)
            .load()
            .migrate()
    }
    override fun hentNotifikasjoner(fnr: String): Sequence<Notifikasjon> =
        sequence {
            log.info("henter notifikasjonfnr")
            val connection = dataSource.connection
            val prepstat = connection.prepareStatement(
                """
                SELECT * from notifikasjon
                WHERE fnr = ?
            """
            )

            prepstat.setString(1, fnr)
            val resultSet = prepstat.executeQuery()
            while (resultSet.next()) {
                yield(
                    Notifikasjon(
                        tjenestenavn = resultSet.getString("tjenestenavn"),
                        eventid = resultSet.getString("eventid"),
                        fnr = resultSet.getString("fnr"),
                        orgnr = resultSet.getString("orgnr"),
                        servicecode = resultSet.getString("servicecode"),
                        serviceedition = resultSet.getString("serviceedition"),
                        tidspunkt = resultSet.getString("tidspunkt"),
                        synlig_frem_til = resultSet.getString("synlig_frem_til"),
                        tekst = resultSet.getString("tekst"),
                        link = resultSet.getString("link"),
                        grupperingsid = resultSet.getString("grupperingsid")
                    )
                )
            }
            resultSet.close()
            connection.close()
        }
}



