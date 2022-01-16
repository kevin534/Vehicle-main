CREATE TABLE IF NOT EXISTS veicoli (
id_veicolo INT AUTO_INCREMENT,
modello VARCHAR(50) NOT NULL,
descrizione VARCHAR(200) DEFAULT NULL,
tipologia VARCHAR(20) NOT NULL,
alimentazione VARCHAR(20) NOT NULL,
posizione VARCHAR(100) DEFAULT NULL,
disponibilita BOOLEAN DEFAULT true,
immagine VARCHAR(100) DEFAULT NULL,
kilowat int not null, 
id_utente INT NOT NULL,
PRIMARY KEY(id_veicolo)
);