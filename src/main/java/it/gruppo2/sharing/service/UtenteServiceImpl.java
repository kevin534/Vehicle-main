package it.gruppo2.sharing.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppo2.sharing.entities.Utente;
import it.gruppo2.sharing.entities.Veicolo;
import it.gruppo2.sharing.model.ModelloUtente;
import it.gruppo2.sharing.repos.UtenteDAO;

@Service
public class UtenteServiceImpl implements UtenteService {
	
	@Autowired
	UtenteDAO repo; 

	@Override
	public List<Utente> trovaTutti(){
		return repo.findAll();
	}

	@Override
	public Utente trovaById(int id) {
		return repo.findById(id).get();
	}

	@Override
	public Utente trovaByEmail(String email) {
		return repo.findByEmail(email);
	}

	@Override
	public Utente trovaByEmailAndPasword(String email, String password) {
		return  repo.findByEmailAndPassword(email, password); 	
	}

	@Override
	public List<Utente> trovaUtenti(char tipo) {
		return repo.findByTipo(tipo);
	}

	@Override
	public void eliminaUtente(int id) {
		repo.deleteById(id);
	}

	@Override
	public List<Utente> impagina(List<Utente> utenti, int numPagina, int dimLista) {
		int offset = numPagina * dimLista;
		List<Utente> impaginati = new ArrayList<Utente>();
		for (int indice = 0; indice < dimLista; indice++) {
			if(indice < utenti.size()) {
				impaginati.add(utenti.get(indice + offset)) ;
			}
		}
		return impaginati;
	}

	@Override
	public boolean checkMail(String email) {
		for (Utente utente : repo.findAll()) {
			if(utente.getEmail().equals(email)){
				return false; 
			}
		}
		
		return true;
	}

	@Override
	public void gestisciUtente(Utente utente) {
		repo.save(utente);
	}

	@Override
	public void aggiungiUtente(Utente utenteRegistrato) {
		repo.save(utenteRegistrato);
	}

	@Override
	public void modificaUtente(int id_utente, ModelloUtente modelloUtente) {
			Utente u = repo.findById(id_utente).get(); 
			
			if(modelloUtente.getCognome()!=null) {
				u.setCognome(modelloUtente.getCognome());
			}
			if(modelloUtente.getNome()!=null) {
				u.setNome(modelloUtente.getNome());
			}
			if(modelloUtente.getEmail()!=null) {
				u.setEmail(modelloUtente.getEmail());
			}
			if(modelloUtente.getPassword()!=null) {
				u.setPassword(modelloUtente.getPassword());
			}
			
			
	}

}
