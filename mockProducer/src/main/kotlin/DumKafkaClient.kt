import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

private fun createProducer(): Producer<String, String> {
    val props = Properties()
    props["bootstrap.servers"] = System.getenv("KAFKA_BROKERS")
    props["key.serializer"] = StringSerializer::class.java.canonicalName
    props["value.serializer"] = StringSerializer::class.java.canonicalName
    props["ssl.keystore.location"] = System.getenv("KAFKA_KEYSTORE_PATH")
    props["ssl.keystore.password"] = System.getenv("KAFKA_CREDSTORE_PASSWORD")
    props["ssl.truststore.location"] = System.getenv("KAFKA_TRUSTSTORE_PATH")
    props["ssl.truststore.password"] = System.getenv("KAFKA_CREDSTORE_PASSWORD")
    props["security.protocol"] = System.getenv("SSL")
    return KafkaProducer<String, String>(props)
}

object PRODUCER {
    val producer = createProducer()
    fun send(msg:String){
        producer.send(ProducerRecord("arbeidsgiver-notifikasjon",msg))
    }

}