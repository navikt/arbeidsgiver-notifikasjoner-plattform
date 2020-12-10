package no.nav.arbeidsgiver.notifikasjoner.kafkastrøm

import no.nav.arbeidsgiver.notifikasjoner.model.Notifikasjon

interface Notifikasjonsstrøm {
    suspend fun hentNotifikasjoner(): Sequence<Notifikasjon>
}