package no.nav.arbeidsgiver.notifikasjon

import org.slf4j.LoggerFactory
import java.sql.Connection
import java.sql.ResultSet
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
    prepstat.setString(1, nokkel.tjenestenavn.toString())
    prepstat.setString(2, nokkel.eventId.toString())

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

typealias DomeneNotifikasjon = no.nav.arbeidsgiver.notifikasjon.model.Notifikasjon

fun ResultSet.forEach(body: (r: ResultSet) -> Unit) {
    this.use {
        while (this.next()) {
            body(this)
        }
    }
}

fun Connection.finnNotifikasjon(fnr: String): List<DomeneNotifikasjon> {
    log.info("henter notifikasjonfnr")
    val prepstat = this.prepareStatement("""
        SELECT * from notifikasjon
        WHERE fnr = ?
    """)

    prepstat.setString(1, fnr)
    val listeMedNotifikasjoner: MutableList<DomeneNotifikasjon> = mutableListOf()
    prepstat.executeQuery().forEach { row ->
        val notifikasjon = DomeneNotifikasjon(
            tjenestenavn = row.getString("tjenestenavn"),
            eventid = row.getString("eventid"),
            fnr = row.getString("fnr"),
            orgnr = row.getString("orgnr"),
            servicecode = row.getString("servicecode"),
            serviceedition = row.getString("serviceedition"),
            tidspunkt = row.getString("tidspunkt"),
            synlig_frem_til = row.getString("synlig_frem_til"),
            tekst = row.getString("tekst"),
            link = row.getString("link"),
            grupperingsid = row.getString("grupperingsid")
        )
        listeMedNotifikasjoner.add(notifikasjon)
    }
    return listeMedNotifikasjoner.toList()
}