package it.gruppo2.sharing.service;

import java.util.List;

import it.gruppo2.sharing.entities.Prenotazione;
import it.gruppo2.sharing.model.Prenotation;

public interface PrenotazioneService {
	
	List<Prenotazione> torvaTutte(); 
	Prenotazione trovaUna(int id);
	List<Prenotazione> findById_utente(int idUtente);
	void addPrenotazione(Prenotazione p);
//	boolean controllaDate(Prenotation prenotation, int veicolo_id);
	void elimina(int id_prenotazione);
	boolean checkPrenotazione(int id_utente);
	List<Prenotazione> trovaStorico(int id_utente);
 
	

}
