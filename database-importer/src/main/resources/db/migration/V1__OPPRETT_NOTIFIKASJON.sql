CREATE TABLE notifikasjon (
    id BIGSERIAL PRIMARY KEY,
    tjenestenavn CHAR(100) NOT NULL,
    eventid CHAR(50) NOT NULL,
    fnr CHAR(11) NULL,
    orgnr CHAR(9) NOT NULL,
    servicecode CHAR(4) NULL,
    serviceedition CHAR(5) NULL,
    tidspunkt BIGINT NOT NULL,
    synlig_frem_til BIGINT NULL,
    tekst TEXT NOT NULL,
    link TEXT NULL,
    grupperingsid CHAR(100) NULL
);
