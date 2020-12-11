package no.nav.arbeidsgiver.notifikasjoner

import no.nav.arbeidsgiver.notifikasjoner.kafkastrøm.NotifikasjonsstrømMocked
import no.nav.arbeidsgiver.notifikasjoner.persistence.NotifikasjonslagerInMemory

fun main(@Suppress("UNUSED_PARAMETER") args: Array<String>) =
    startServer(
        lagNotifikasjonsstrøm = ::NotifikasjonsstrømMocked,
        lagNotifikasjonslager = ::NotifikasjonslagerInMemory
    )

