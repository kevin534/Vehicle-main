CREATE TABLE IF NOT EXISTS archivio_utenti (
id_utente INT AUTO_INCREMENT,
nome VARCHAR(40),
cognome VARCHAR(40),
data_nascita DATE,
email VARCHAR(40) NOT NULL UNIQUE,
Password VARCHAR(50) NOT NULL,
tipo VARCHAR(1) NOT NULL,
ultima_modifica TIMESTAMP,
data_iscrizione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT id_utente PRIMARY KEY (id_utente),
UNIQUE KEY IDX_Utente_1 (id_utente)
);