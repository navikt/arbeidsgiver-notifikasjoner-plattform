package no.nav.arbeidsgiver.notifikasjoner.kafkastrøm

import io.confluent.kafka.serializers.KafkaAvroDeserializer
import no.nav.arbeidsgiver.notifikasjoner.skjema.Beskjed
import no.nav.arbeidsgiver.notifikasjoner.skjema.OrgnrAltinnservice
import no.nav.arbeidsgiver.notifikasjoner.skjema.OrgnrFnr
import no.nav.arbeidsgiver.notifikasjoner.skjema.Nokkel
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.net.URI
import java.net.URL
import java.time.Duration
import java.util.*

typealias AvroNotifikasjon = no.nav.arbeidsgiver.notifikasjoner.skjema.Notifikasjon
typealias DomeneNotifikasjon = no.nav.arbeidsgiver.notifikasjoner.model.Notifikasjon

class NotifikasjonsstrømKafka : Notifikasjonsstrøm {
    private val kafkaConsumer = lagKafkaConsumer()

    override suspend fun hentNotifikasjoner(): Sequence<DomeneNotifikasjon> {
        return kafkaConsumer.poll(Duration.ofMillis(1000))
            .asSequence()
            .map { avroTilDomeneConverter(it.key(), it.value()) }
    }
}

private fun avroTilDomeneConverter(nokkel: Nokkel, notifikasjon: AvroNotifikasjon): DomeneNotifikasjon {
    val mottaker = notifikasjon.mottaker.mottaker
    val notifikasjonstype = notifikasjon.notifikasjon
    return DomeneNotifikasjon(
        tjenestenavn = nokkel.tjenestenavn,
        eventid = nokkel.eventId,
        fnr = (mottaker as? OrgnrFnr)?.fnr,
        orgnr = when (mottaker) {
            is OrgnrFnr -> mottaker.orgnr
            is OrgnrAltinnservice -> mottaker.orgnr
            else -> throw IllegalArgumentException("unknown mottaker")
        },
        servicecode = (mottaker as? OrgnrAltinnservice)?.servicecode,
        serviceedition = (mottaker as? OrgnrAltinnservice)?.serviceedition,
        grupperingsid = notifikasjon.grupperingsId,
        tidspunkt = notifikasjon.tidspunkt.toString(),
        synlig_frem_til = notifikasjon.synligFremTil?.toString(),
        tekst = (notifikasjonstype as Beskjed).tekst,
        link = notifikasjonstype.link
    )
}


private fun lagKafkaConsumer(): KafkaConsumer<Nokkel, AvroNotifikasjon> {
    val props = Properties()
    props["bootstrap.servers"] = System.getenv("KAFKA_BROKERS") ?: ""
    props["key.deserializer"] = KafkaAvroDeserializer::class.java.canonicalName
    props["value.deserializer"] = KafkaAvroDeserializer::class.java.canonicalName
    props["ssl.keystore.location"] = System.getenv("KAFKA_KEYSTORE_PATH") ?: ""
    props["ssl.keystore.password"] = System.getenv("KAFKA_CREDSTORE_PASSWORD") ?: ""
    props["ssl.truststore.location"] = System.getenv("KAFKA_TRUSTSTORE_PATH") ?: ""
    props["ssl.truststore.password"] = System.getenv("KAFKA_CREDSTORE_PASSWORD") ?: ""
    props["security.protocol"] = "SSL"
    val schemaRegistry = URL(System.getenv("KAFKA_SCHEMA_REGISTRY"))
    props["basic.auth.credentials.source"] = "USER_INFO"
    val schemausr = System.getenv("KAFKA_SCHEMA_REGISTRY_USER")
    val schemapass = System.getenv("KAFKA_SCHEMA_REGISTRY_PASSWORD")
    val schemaregusrinfo = "$schemausr:$schemapass"
    props["basic.auth.user.info"] = schemaRegistry.userInfo ?: schemaregusrinfo
    props["group.id"] = "notifikasjon-frontend-api"
    props["specific.avro.reader"] = "true"
    props["schema.registry.url"] = URI(
        schemaRegistry.protocol,
        null,
        schemaRegistry.host,
        schemaRegistry.port,
        schemaRegistry.path,
        schemaRegistry.query,
        schemaRegistry.ref
    ).toString()

    val consumer = KafkaConsumer<Nokkel, AvroNotifikasjon>(props)
    consumer.subscribe(listOf("arbeidsgiver.notifikasjoner"))
    return consumer
}
