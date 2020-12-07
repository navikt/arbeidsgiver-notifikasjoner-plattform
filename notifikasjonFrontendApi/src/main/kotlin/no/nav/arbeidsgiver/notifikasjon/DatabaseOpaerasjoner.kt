package no.nav.arbeidsgiver.notifikasjon

import org.slf4j.LoggerFactory
import java.sql.Connection
import javax.sql.DataSource

private val log = LoggerFactory.getLogger("DatabaseOperasjoner")

class UnhandeledTransactionRollback(msg: String, e: Throwable) : Exception(msg, e)

fun <T>defaultRollback(e: Exception): T {
    throw UnhandeledTransactionRollback("no rollback function registered", e)
}

fun <T>Connection.transaction(rollback: (e: Exception) -> T = ::defaultRollback, body: () -> T): T {
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

fun <T> DataSource.transaction(rollback: (e: Exception) -> T = ::defaultRollback, body: (c: Connection) -> T): T =
    connection.use { c ->
        c.transaction(rollback) {
            body(c)
        }
    }

fun Connection.leggTilNotifikasjon(nokkel: Nokkel, notifikasjonWrapper: Notifikasjon) {
    log.info("Lagrer notifikasjon til database (nøkkel {} {})", nokkel.tjenestenavn, nokkel.eventId)
    val prepstat = this.prepareStatement("""
        insert into notifikasjon values (
            tjenestenavn=?, 
            eventid=?, 
            fnr=?, 
            orgnr=?, 
            servicecode=?, 
            serviceedition=?, 
            tidspunkt=?, 
            synlig_frem_til=?, 
            tekst=?, 
            link=?, 
            grupperingsid=?
        )
    """)
    prepstat.setString(1, nokkel.tjenestenavn.toString())
    prepstat.setString(2,nokkel.eventId.toString())

    when (val mottaker = notifikasjonWrapper.mottaker.mottaker) {
        is OrgnrFnr -> {
            prepstat.setString(3, mottaker.fnr.toString())
            prepstat.setString(4, mottaker.orgnr.toString())
            prepstat.setString(5, null)
            prepstat.setString(6, null)
        }
        is OrgnrAltinnservice -> {
            prepstat.setString(3, null)
            prepstat.setString(4, mottaker.orgnr.toString())
            prepstat.setString(5, mottaker.servicecode.toString())
            prepstat.setString(6, mottaker.serviceedition.toString())
        }
    }

    prepstat.setLong(7, notifikasjonWrapper.tidspunkt)
    when (val notifikasjon = notifikasjonWrapper.notifikasjon) {
        is Beskjed -> {
            prepstat.setObject( 8, notifikasjon.synligFremTil)
            prepstat.setString(9, notifikasjon.tekst.toString())
            prepstat.setString(10, notifikasjon.link.toString())
        }
        else -> {
            throw RuntimeException("ukjent notifikasjontype")
        }
    }
    prepstat.setString(11, notifikasjonWrapper.grupperingsId.toString())
    prepstat.execute()
}