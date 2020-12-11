package no.nav.arbeidsgiver.notifikasjoner.kafkastrøm

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.nav.arbeidsgiver.notifikasjoner.model.Notifikasjon
import java.lang.Integer.min
import java.util.*
import kotlin.random.Random

class NotifikasjonsstrømMocked : Notifikasjonsstrøm {
    private val notifikasjoner = mutableListOf<Notifikasjon>()
    private var index = 0

    init {
        GlobalScope.launch {
            while (true) {
               delay(Random.nextLong(1_000, 10_000))
                repeat(Random.nextInt(0, 10)) {
                    notifikasjoner.add(randomNotifikasjon())
                }
            }
        }
    }

    private fun randomNotifikasjon() =
        Notifikasjon(
            tjenestenavn = "test",
            eventid = UUID.randomUUID().toString(),
            tidspunkt = Date().time.toString(),
            fnr = "44444444",
            orgnr = "123",
            tekst = "en beskjed til deg",
            link = "lenke",
            servicecode = null,
            serviceedition = null,
            synlig_frem_til = null,
            grupperingsid = null
        )

    override suspend fun hentNotifikasjoner(): Sequence<Notifikasjon> {
        delay(900)
        return if (index < notifikasjoner.size) {
            val nyIndex = min(notifikasjoner.size, index + 10)
            val uleste = notifikasjoner.slice(IntRange(index, nyIndex - 1))
            index = nyIndex
            uleste.asSequence()
        } else {
            emptySequence()
        }
    }
}