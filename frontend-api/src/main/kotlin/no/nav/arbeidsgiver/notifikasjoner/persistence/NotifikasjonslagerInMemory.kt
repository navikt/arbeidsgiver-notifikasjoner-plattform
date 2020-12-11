package no.nav.arbeidsgiver.notifikasjoner.persistence

import no.nav.arbeidsgiver.notifikasjoner.model.Notifikasjon

class NotifikasjonslagerInMemory : Notifikasjonslager {

    private val notifikasjoner = mutableListOf<Notifikasjon>()

    override fun hentNotifikasjoner(fnr: String): Sequence<Notifikasjon> =
        notifikasjoner
            .filter { it.fnr == fnr }
            .asSequence()

    override fun leggTilNotifikasjoner(notifikasjoner: Sequence<Notifikasjon>) {
        this.notifikasjoner.addAll(notifikasjoner)
    }
}