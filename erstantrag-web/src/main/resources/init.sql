DROP TABLE IF EXISTS partner;

CREATE TABLE partner
(
    id                          BIGSERIAL PRIMARY KEY,
    partnernummer               VARCHAR(255) NOT NULL,
    vorname                     VARCHAR(255) NOT NULL,
    nachname                    VARCHAR(255) NOT NULL,
    geburtsdatum                varchar(10)  NOT NULL,
    geburtsort                  VARCHAR(255) NOT NULL,
    staatsangehoerigkeit        VARCHAR(255) NOT NULL,
    strasse                     VARCHAR(255) NOT NULL,
    plz                         VARCHAR(10)  NOT NULL,
    ort                         VARCHAR(255) NOT NULL,
    telefon                     VARCHAR(20)  NOT NULL,
    email                       VARCHAR(255) NOT NULL,
    steueridentifikationsnummer VARCHAR(20)  NOT NULL,
    versicherungsstatus         VARCHAR(255) NOT NULL,
    rentenversicherungsnummer   VARCHAR(255) NOT NULL,
    krankenkasse                VARCHAR(255) NOT NULL,
    name_kontoinhaber           VARCHAR(255) NOT NULL,
    iban                        VARCHAR(34)  NOT NULL
);

DROP TABLE IF EXISTS erstantrag;

CREATE TABLE erstantrag
(
    id                                 BIGSERIAL PRIMARY KEY,
    antragnummer                       VARCHAR(255),
    vorname                            VARCHAR(255) NOT NULL,
    nachname                           VARCHAR(255) NOT NULL,
    geburtsdatum                       VARCHAR(10)  NOT NULL,
    geburtsort                         VARCHAR(255) NOT NULL,
    staatsangehoerigkeit               VARCHAR(255) NOT NULL,
    strasse                            VARCHAR(255) NOT NULL,
    plz                                VARCHAR(10)  NOT NULL,
    ort                                VARCHAR(255) NOT NULL,
    telefon                            VARCHAR(20)  NOT NULL,
    email                              VARCHAR(255) NOT NULL,
    gewuenschter_rentenbeginn          VARCHAR(255) NOT NULL,
    bescheid_rentenversicherung        BOOLEAN      NOT NULL,
    sonstiger_nachweis                 BOOLEAN      NOT NULL,
    besondere_vereinbarungen_vorhanden BOOLEAN      NOT NULL,
    besondere_vereinbarungen           TEXT,
    steueridentifikationsnummer        VARCHAR(20)  NOT NULL,
    auszahlung_dienstverhaeltnis       VARCHAR(255) NOT NULL,
    versicherungsstatus                VARCHAR(255) NOT NULL,
    rentenversicherungsnummer          VARCHAR(255) NOT NULL,
    krankenkasse                       VARCHAR(255) NOT NULL,
    erfuellung_elterneigenschaft       BOOLEAN      NOT NULL,
    name_kinder                        TEXT,
    name_kontoinhaber                  VARCHAR(255) NOT NULL,
    iban                               VARCHAR(34)  NOT NULL,
    anmerkungen                        TEXT
);

DROP TABLE IF EXISTS vector_store;
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store
(
    id        uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content   text,
    metadata  json,
    embedding vector(1024)
);

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);