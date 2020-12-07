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
    val logger = LoggerFactory.getLogger("kafkaToDatabaseService")!!

    while (true) {
        val notifikasjoner = kafkaConsumer.poll(Duration.ofMillis(1_000))

        dataSource.transaction { connection ->
            notifikasjoner.forEach {
                logger.info("mottatt beskjed: {} ", it.value())
                connection.leggTilNotifikasjon(it.key(), it.value())
            }
        }

        logger.info("Overført {} notifikasjoner fra kafka til database", notifikasjoner.count())
    }
}

