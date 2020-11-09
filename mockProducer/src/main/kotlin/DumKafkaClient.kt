import io.confluent.kafka.serializers.KafkaAvroSerializer
import no.nav.arbeidsgiver.Notifikasjon
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.URL
import java.util.*

private fun createProducer(): Producer<String, Notifikasjon> {
    val props = Properties()
    props["bootstrap.servers"] = System.getenv("KAFKA_BROKERS") ?: ""
    props["key.serializer"] = StringSerializer::class.java.canonicalName
    props["value.serializer"] = KafkaAvroSerializer::class.java.canonicalName
    props["ssl.keystore.location"] = System.getenv("KAFKA_KEYSTORE_PATH") ?: ""
    props["ssl.keystore.password"] = System.getenv("KAFKA_CREDSTORE_PASSWORD") ?: ""
    props["ssl.truststore.location"] = System.getenv("KAFKA_TRUSTSTORE_PATH") ?: ""
    props["ssl.truststore.password"] = System.getenv("KAFKA_CREDSTORE_PASSWORD") ?: ""
    props["security.protocol"] = "SSL"

    val schemaRegistry = URL(System.getenv("KAFKA_SCHEMA_REGISTRY"))
    props["basic.auth.credentials.source"] = "USER_INFO"
    props["basic.auth.user.info"] = schemaRegistry.userInfo
    props["schema.registry.url"] = URI(
        schemaRegistry.protocol,
        null,
        schemaRegistry.host,
        schemaRegistry.port,
        schemaRegistry.path,
        schemaRegistry.query,
        schemaRegistry.ref
    ).toString()

    return KafkaProducer(props)
}

object PRODUCER {
    var logger = LoggerFactory.getLogger("PRODUCER")!!

    var producer: Producer<String, Notifikasjon> = try {
        createProducer()
    } catch (e: Exception) {
        logger.error("Konstruksjon av producer feilet", e)
        throw e
    }

    fun send(msg:String) {
        val notifikasjon=Notifikasjon()
        notifikasjon.id=UUID.randomUUID().toString()
        notifikasjon.orgnr="910825526"
        notifikasjon.servicecode="5441"
        notifikasjon.serviceedition=1
        notifikasjon.beskjed="msg"
        logger.info("send({}): producer={}", msg, producer)
        producer.send(ProducerRecord("arbeidsgiver.arbeidsgiver-notifikasjon",notifikasjon.id.toString(), notifikasjon))

    }
}