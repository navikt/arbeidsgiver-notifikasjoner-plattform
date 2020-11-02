import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.util.*

private fun createProducer(): Producer<String, String> {
    val props = Properties()
    props["bootstrap.servers"] = System.getenv("KAFKA_BROKERS") ?: ""
    props["key.serializer"] = StringSerializer::class.java.canonicalName
    props["value.serializer"] = StringSerializer::class.java.canonicalName
    props["ssl.keystore.location"] = System.getenv("KAFKA_KEYSTORE_PATH") ?: ""
    props["ssl.keystore.password"] = System.getenv("KAFKA_CREDSTORE_PASSWORD") ?: ""
    props["ssl.truststore.location"] = System.getenv("KAFKA_TRUSTSTORE_PATH") ?: ""
    props["ssl.truststore.password"] = System.getenv("KAFKA_CREDSTORE_PASSWORD") ?: ""
    props["security.protocol"] = "SSL"
    return KafkaProducer(props)
}

object PRODUCER {
    var logger = LoggerFactory.getLogger("PRODUCER")!!
    var producer: Producer<String, String> = try {
        createProducer()
    } catch (e: Exception) {
        logger.error("Konstruksjon av producer feilet", e)
        throw e
    }

    fun send(msg:String) {
        logger.info("send({}): producer={}", msg, producer)
        producer.send(ProducerRecord("arbeidsgiver.arbeidsgiver-notifikasjon", msg))
    }
}