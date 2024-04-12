CREATE TABLE utilisateur
(
    id INT8 PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenoms VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    statut VARCHAR(100) NOT NULL DEFAULT 'ACTIF',
    create_at      TIMESTAMP,
    create_by     VARCHAR(255),
    update_at  TIMESTAMP,
    update_by VARCHAR(255),
    version INT8 NOT NULL DEFAULT 0
);
CREATE SEQUENCE utilisateur_id_SEQ INCREMENT BY 50 START 1;
ALTER TABLE utilisateur
    ALTER COLUMN id SET DEFAULT nextval('utilisateur_id_seq');