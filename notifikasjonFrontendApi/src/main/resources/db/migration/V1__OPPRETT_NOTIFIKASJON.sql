CREATE TABLE IF NOT EXISTS notifikasjon
(
    id  INT PRIMARY KEY,
    beskjed TEXT NOT NULL,
    orgnr CHAR(9) NOT NULL,
    servicecode VARCHAR(4) NOT NULL,
    serviceedition INT NOT NULL
);