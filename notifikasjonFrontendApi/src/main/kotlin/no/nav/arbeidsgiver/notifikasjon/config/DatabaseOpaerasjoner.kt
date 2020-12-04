package no.nav.arbeidsgiver.notifikasjon.config

import no.nav.arbeidsgiver.notifikasjon.*
import java.sql.Connection
import javax.sql.DataSource


fun Connection.leggTilNotifikasjon(nokkel: Nokkel, notifikasjonWrapper: Notifikasjon) {
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
            prepstat.setString(3, mottaker.fnr.toString());
            prepstat.setString(4, mottaker.orgnr.toString())
        }
        is OrgnrAltinnservice -> {
            prepstat.setString(4, mottaker.orgnr.toString())
            prepstat.setString(5, mottaker.servicecode.toString())
            prepstat.setString(6, mottaker.serviceedition.toString())
        }
    }

    prepstat.setLong(7, notifikasjonWrapper.tidspunkt);
    when (val notifikasjon = notifikasjonWrapper.notifikasjon) {
        is Beskjed -> {
            prepstat.setLong(8, notifikasjon.synligFremTil);
            prepstat.setString(9, notifikasjon.tekst.toString());
            prepstat.setString(10, notifikasjon.link.toString());
        }
    }
    prepstat.setString(11, notifikasjonWrapper.grupperingsId.toString());
    prepstat.execute();
}