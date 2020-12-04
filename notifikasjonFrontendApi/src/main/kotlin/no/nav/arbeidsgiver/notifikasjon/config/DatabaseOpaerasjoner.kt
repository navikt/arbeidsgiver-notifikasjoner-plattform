package no.nav.arbeidsgiver.notifikasjon.config

import no.nav.arbeidsgiver.notifikasjon.Nokkel
import no.nav.arbeidsgiver.notifikasjon.Notifikasjon
import java.sql.Connection
import javax.sql.DataSource


fun Connection.leggTilNotifikasjon (nokkel: Nokkel, notifikasjon: Notifikasjon){
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
    prepstat.setString(2,nokkel.fnr.toString())
    prepstat.setString(2,nokkel.orgnr.toString())
    prepstat.setString(2,nokkel.servicecode.toString())
    prepstat.setString(2,nokkel.serviceedition.toString())
    prepstat.setString(2,nokkel.tidspunkt.toString())
    prepstat.setString(2,nokkel.synlig_frem_til.toString())
    prepstat.setString(2,nokkel.tekst.toString())
    prepstat.setString(2,nokkel.link.toString())
    prepstat.setString(2,nokkel.grupperingsid.toString())

}