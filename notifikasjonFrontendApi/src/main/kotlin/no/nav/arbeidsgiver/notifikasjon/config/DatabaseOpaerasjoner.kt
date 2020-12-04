package no.nav.arbeidsgiver.notifikasjon.config

import no.nav.arbeidsgiver.notifikasjon.Nokkel
import no.nav.arbeidsgiver.notifikasjon.Notifikasjon
import java.sql.Connection
import javax.sql.DataSource


fun Connection.leggTilNotifikasjon (nokkel: Nokkel, notifikasjon: Notifikasjon){
    var prepstat = this.prepareStatement("insert into notifikasjon values (id=?)")
    prepstat.setInt(1,1)

}