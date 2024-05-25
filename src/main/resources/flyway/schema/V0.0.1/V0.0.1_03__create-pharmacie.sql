CREATE TABLE pharmacie
(
    id INT8 PRIMARY KEY,
    numero VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    ville VARCHAR(100) NOT NULL,
    quartier VARCHAR(100) NOT NULL,
    heure_ouverture TIME         NOT NULL,
    heure_fermeture TIME         NOT NULL,
    nom_gerant      VARCHAR(100) NOT NULL,
    contact VARCHAR(100) NOT NULL,
    statut VARCHAR(100) NOT NULL DEFAULT 'ACTIF',
    latitude VARCHAR(100),
    longitude VARCHAR(100),
    create_at      TIMESTAMP,
    create_by     VARCHAR(255),
    update_at  TIMESTAMP,
    update_by VARCHAR(255),
    version INT8 NOT NULL DEFAULT 0
);
CREATE SEQUENCE pharmacie_id_seq INCREMENT BY 50 START 1;
ALTER TABLE pharmacie
    ALTER COLUMN id SET DEFAULT nextval('pharmacie_id_seq');