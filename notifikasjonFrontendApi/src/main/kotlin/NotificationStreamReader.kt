import io.confluent.kafka.serializers.KafkaAvroDeserializer
import no.nav.arbeidsgiver.notifikasjon.Nokkel
import no.nav.arbeidsgiver.notifikasjon.Notifikasjon
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.net.URI
import java.net.URL
import java.util.*

fun lagKafkaConsumer(): KafkaConsumer<Nokkel, Notifikasjon> {
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
    props["schema.registry.url"] = URI(
            schemaRegistry.protocol,
            null,
            schemaRegistry.host,
            schemaRegistry.port,
            schemaRegistry.path,
            schemaRegistry.query,
            schemaRegistry.ref
    ).toString()
    return KafkaConsumer(props)
}



