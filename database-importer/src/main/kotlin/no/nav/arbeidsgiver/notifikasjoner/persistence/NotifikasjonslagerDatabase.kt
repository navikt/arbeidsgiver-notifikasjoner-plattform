package no.nav.arbeidsgiver.notifikasjoner.persistence

import com.zaxxer.hikari.HikariDataSource
import no.nav.arbeidsgiver.notifikasjoner.model.Notifikasjon
import org.flywaydb.core.Flyway
import org.slf4j.LoggerFactory
import java.sql.Connection
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

    override fun leggTilNotifikasjoner(notifikasjoner: Sequence<Notifikasjon>) {
        dataSource.transaction { connection ->
            notifikasjoner.forEach {
                connection.leggTilNotifikasjon(it)
            }
        }
    }

    private fun Connection.leggTilNotifikasjon(notifikasjon: Notifikasjon) {
        log.info("Lagrer notifikasjon til database (nøkkel {} {})", notifikasjon.tjenestenavn, notifikasjon.eventid)
        val prepstat = this.prepareStatement("""
            INSERT INTO notifikasjon(
                tjenestenavn,
                eventid,
                fnr,
                orgnr,
                servicecode,
                serviceedition,
                tidspunkt,
                synlig_frem_til,
                tekst,
                link,
                grupperingsid
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """)
        prepstat.setString(1, notifikasjon.tjenestenavn)
        prepstat.setString(2, notifikasjon.eventid)
        prepstat.setString(3, notifikasjon.fnr)
        prepstat.setString(4, notifikasjon.orgnr)
        prepstat.setString(5, notifikasjon.servicecode)
        prepstat.setString(6, notifikasjon.serviceedition)
        prepstat.setLong(7, notifikasjon.tidspunkt.toLong())
        prepstat.setObject( 8, notifikasjon.synlig_frem_til)
        prepstat.setString(9, notifikasjon.tekst)
        prepstat.setString(10, notifikasjon.link)
        prepstat.setString(11, notifikasjon.grupperingsid)
        prepstat.execute()
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

class UnhandeledTransactionRollback(msg: String, e: Throwable) : Exception(msg, e)

private fun <T>defaultRollback(e: Exception): T {
    throw UnhandeledTransactionRollback("no rollback function registered", e)
}

private fun <T> Connection.transaction(rollback: (e: Exception) -> T = ::defaultRollback, body: () -> T): T {
    val savedAutoCommit = autoCommit
    autoCommit = false

    return try {
        val result = body()
        commit()
        result
    } catch (e: Exception) {
        rollback(e)
    } finally {
        autoCommit = savedAutoCommit
    }
}

private fun <T> DataSource.transaction(rollback: (e: Exception) -> T = ::defaultRollback, body: (c: Connection) -> T): T =
    connection.use { c ->
        c.transaction(rollback) {
            body(c)
        }
    }


