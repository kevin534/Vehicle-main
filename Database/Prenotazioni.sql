CREATE TABLE IF NOT EXISTS prenotazioni (
    id_prenotazione INT AUTO_INCREMENT,
    id_veicolo INT,
    id_utente INT,
    ora_inizio DATETIME,
    ora_fine DATETIME,
    PRIMARY KEY (id_prenotazione)
);