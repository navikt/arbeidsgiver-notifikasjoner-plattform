package no.nav.arbeidsgiver.notifikasjon.service

import no.nav.arbeidsgiver.notifikasjon.Nokkel
import no.nav.arbeidsgiver.notifikasjon.Notifikasjon
import no.nav.arbeidsgiver.notifikasjon.leggTilNotifikasjon
import no.nav.arbeidsgiver.notifikasjon.transaction
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import java.time.Duration
import javax.sql.DataSource


fun kafkaToDatabaseService(kafkaConsumer: KafkaConsumer<Nokkel, Notifikasjon>, dataSource: DataSource) {
    val log = LoggerFactory.getLogger("kafkaToDatabaseService")!!

    log.info("kafka til database service startet opp")

    while (true) {
        val notifikasjoner = kafkaConsumer.poll(Duration.ofMillis(1_000))

        dataSource.transaction { connection ->
            notifikasjoner.forEach {
                log.info("mottatt beskjed: {} ", it.value())
                connection.leggTilNotifikasjon(it.key(), it.value())
            }
        }

        log.info("Overført {} notifikasjoner fra kafka til database", notifikasjoner.count())
    }
}

