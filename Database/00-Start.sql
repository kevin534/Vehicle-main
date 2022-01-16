CREATE DATABASE e_anduma; 
GRANT all privileges ON e_anduma.* TO 'admin'@'localhost' identified by 'admin';
flush privileges;

USE e_anduma;

SOURCE D:\Files\Workspace\Eclipse\Project Work\Vehicle_Sharing\Database\archivio_utenti.sql

SOURCE D:\Files\Workspace\Eclipse\Project Work\Vehicle_Sharing\Database\prenotazioni.sql

SOURCE D:\Files\Workspace\Eclipse\Project Work\Vehicle_Sharing\Database\veicoli.sql

INSERT INTO archivio_utenti (password, tipo, nome, cognome, data_nascita, email) 
VALUES ("Password", "A", "Davide", "Piras", "1995-01-12", "davide.piras@gmail.com"),
("Password", "A", "Franky", "Talin", "2000-01-01", "franky.talin@gmail.com"),
("Password", "A", "Michael", "Micheletti", "2000-01-01", "michael.micheletti@gmail.com"),
("Password", "A", "Rebecca", "Ciglieri", "1990-01-01", "rebecca.ciglieri@gmail.com"),
("Password", "A", "Elena", "Bosio", "1990-01-01", "elena.bosio@gmail.com");

INSERT INTO veicoli (produttore, modello, tipologia, alimentazione, disponibilita, id_utente)
VALUES ("Volkswagen", "Scirocco", "Automobile", "Benzina", true, 1);



