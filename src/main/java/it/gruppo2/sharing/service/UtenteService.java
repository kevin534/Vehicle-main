package it.gruppo2.sharing.service;

import java.util.List;

import it.gruppo2.sharing.entities.Utente;
import it.gruppo2.sharing.entities.Veicolo;
import it.gruppo2.sharing.model.ModelloUtente;

public interface UtenteService {
	
	Utente trovaById(int id);
	List<Utente> trovaTutti();
	List<Utente> trovaUtenti(char tipo);
	Utente trovaByEmail(String email);
	Utente trovaByEmailAndPasword(String email, String Password);
	void eliminaUtente(int id);
	boolean checkMail(String email);
	void gestisciUtente(Utente utente);
	void aggiungiUtente(Utente utenteRegistrato);
	void modificaUtente(int id_utente, ModelloUtente modelloUtente);
	List<Utente> impagina(List<Utente> utenti, int numPagina, int dimLista); 
	
	
}
