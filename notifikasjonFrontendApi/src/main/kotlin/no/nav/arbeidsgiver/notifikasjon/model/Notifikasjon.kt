package no.nav.arbeidsgiver.notifikasjon.model

data class Notifikasjon(
        val tjenestenavn: String,
        val eventid: String,
        val fnr: String?,
        val orgnr: String,
        val servicecode: String?,
        val serviceedition: String?,
        val tidspunkt: String,
        val synlig_frem_til: String?,
        val tekst: String,
        val link: String?,
        val grupperingsid: String?
)