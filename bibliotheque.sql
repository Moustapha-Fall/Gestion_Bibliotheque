CREATE DATABASE IF NOT EXISTS bibliotheque;
USE bibliotheque;

-- ----------------------------------------------------------
-- Table : membres
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS membres (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    email        VARCHAR(255),
    nom          VARCHAR(255),
    prenom       VARCHAR(255),
    adresse      VARCHAR(255),
    numeroCarte  VARCHAR(255) UNIQUE,
    quotaEmprunt INT DEFAULT 3
);

-- ----------------------------------------------------------
-- Table : ouvrages
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS ouvrages (
    id              INT PRIMARY KEY AUTO_INCREMENT,
    isbn            VARCHAR(255),
    titre           VARCHAR(255),
    auteur          VARCHAR(255),
    genre           VARCHAR(50),
    datePublication VARCHAR(20)
);

-- ----------------------------------------------------------
-- Table : annexes
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS annexes (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    nom       VARCHAR(255),
    adresse   VARCHAR(255),
    telephone VARCHAR(50)
);

-- ----------------------------------------------------------
-- Table : exemplaires
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS exemplaires (
    id               INT PRIMARY KEY AUTO_INCREMENT,
    dateAcquisition  VARCHAR(20),
    etat             VARCHAR(50) DEFAULT 'DISPONIBLE',
    ouvrageId        INT,
    annexeId         INT
);

-- ----------------------------------------------------------
-- Table : emprunts
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS emprunts (
    id               INT PRIMARY KEY AUTO_INCREMENT,
    dateEmprunt      VARCHAR(20),
    dateRetourPrevue VARCHAR(20),
    dateRetourReelle VARCHAR(20),
    statut           VARCHAR(20) DEFAULT 'EN_COURS',
    membreId         INT,
    exemplaireId     INT
);

-- ----------------------------------------------------------
-- Table : amendes
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS amendes (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    montant   DOUBLE,
    paye      INT DEFAULT 0,
    motif     VARCHAR(50),
    empruntId INT
);

-- ----------------------------------------------------------
-- Table : caisse (une seule ligne)
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS caisse (
    id    INT PRIMARY KEY AUTO_INCREMENT,
    solde DOUBLE DEFAULT 0
);

INSERT IGNORE INTO caisse (id, solde) VALUES (1, 0);

-- ----------------------------------------------------------
-- Table : vehicules
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS vehicules (
    id             INT PRIMARY KEY AUTO_INCREMENT,
    immatriculation VARCHAR(50),
    modele         VARCHAR(100),
    marque         VARCHAR(100),
    kilometrage    DOUBLE DEFAULT 0
);

-- ----------------------------------------------------------
-- Table : conditions_stockage
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS conditions_stockage (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    temperature DOUBLE,
    humidite    DOUBLE,
    dateReleve  VARCHAR(20),
    alerte      INT DEFAULT 0,
    annexeId    INT
);
