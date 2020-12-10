package no.nav.arbeidsgiver.notifikasjoner.service

import no.nav.arbeidsgiver.notifikasjoner.kafkastrøm.Notifikasjonsstrøm
import no.nav.arbeidsgiver.notifikasjoner.persistence.Notifikasjonslager
import org.slf4j.LoggerFactory

suspend fun kafkaToDatabaseService(
    notifikasjonsstrøm: Notifikasjonsstrøm,
    notifikasjonslager: Notifikasjonslager
) {
    val log = LoggerFactory.getLogger("kafkaToDatabaseService")!!

    log.info("kafka til database service startet opp")

    while (true) {
        val notifikasjoner = notifikasjonsstrøm.hentNotifikasjoner()
        notifikasjonslager.leggTilNotifikasjoner(notifikasjoner)

        if (notifikasjoner.count() > 0) {
            log.info("Overført {} notifikasjoner fra kafka til database", notifikasjoner.count())
        }
    }
}

