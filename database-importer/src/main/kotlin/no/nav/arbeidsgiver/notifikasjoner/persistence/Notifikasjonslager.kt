package no.nav.arbeidsgiver.notifikasjoner.persistence

import no.nav.arbeidsgiver.notifikasjoner.model.Notifikasjon

interface Notifikasjonslager {
    fun leggTilNotifikasjoner(notifikasjoner: Sequence<Notifikasjon>)
    fun hentNotifikasjoner(fnr: String): Sequence<Notifikasjon>
}