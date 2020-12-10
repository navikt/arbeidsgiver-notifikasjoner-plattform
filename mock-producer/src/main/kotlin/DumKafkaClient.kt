import io.confluent.kafka.serializers.KafkaAvroSerializer
import no.nav.arbeidsgiver.notifikasjoner.skjema.*
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.URL
import java.util.*

private fun createProducer(): Producer<Nokkel, Notifikasjon> {
    val props = Properties()
    props["bootstrap.servers"] = System.getenv("KAFKA_BROKERS") ?: ""
    props["key.serializer"] = KafkaAvroSerializer::class.java.canonicalName
    props["value.serializer"] = KafkaAvroSerializer::class.java.canonicalName
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

    var producer: Producer<Nokkel, Notifikasjon> = try {
        createProducer()
    } catch (e: Exception) {
        logger.error("Konstruksjon av producer feilet", e)
        throw e
    }

    fun send(msg:String) {
        val nokkel = Nokkel("mockproducer", UUID.randomUUID().toString())
        val notifikasjon = Notifikasjon()
            .apply {
            mottaker = Mottaker(OrgnrFnr().apply {
                orgnr = "111111111"
                fnr = "44444444"
            })
            notifikasjon = Beskjed().apply {
                tekst = "Hello"
                link = "vg.no"
            }
            grupperingsId = "123"
            tidspunkt = 123
        }

        logger.info("send({}): producer={}", msg, producer)
        producer.send(ProducerRecord("arbeidsgiver.notifikasjoner", nokkel, notifikasjon))
    }
}