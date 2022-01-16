package it.gruppo2.sharing.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppo2.sharing.entities.Prenotazione;
import it.gruppo2.sharing.model.Prenotation;
import it.gruppo2.sharing.repos.PrenotazioneDAO;

@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {
	
	@Autowired
	PrenotazioneDAO repo; 
	
	

	@Override
	public List<Prenotazione> torvaTutte() {
		return repo.findAll(); 
	}

	@Override
	public Prenotazione trovaUna(int id) {
		return repo.findById(id).get();
	}

	

	@Override
	public List<Prenotazione> findById_utente(int idUtente) {
		return repo.findById_Utente(idUtente);
	}

	@Override
	public void addPrenotazione(Prenotazione p) {
		repo.save(p); 
		
	}

	@Override
	public void elimina(int id_prenotazione) {
	
		repo.deleteById(id_prenotazione);
		
	}

	@Override
	public boolean checkPrenotazione(int id_utente) {
		for (Prenotazione prenotazione : repo.findAll()) {
			if(prenotazione.getId_utente()==id_utente && prenotazione.isStatus()) {
				return true; 
			}
		}
		return false;
	}

	@Override
	public List<Prenotazione> trovaStorico(int id_utente) {
		List<Prenotazione> storico =  new ArrayList<Prenotazione>(); 
		for(Prenotazione p: repo.findAll()) {
			if(p.getId_utente()==id_utente && !(p.isStatus())) {
				storico.add(p); 
			}
		}
		return storico; 
	}

	

//	@Override
//	public boolean controllaDate(Prenotation prenotation, int veicolo_id) {
//		
//		Date data = Date.valueOf(prenotation.getStart_date()); 
//		Time oraInizio = Time.valueOf(prenotation.getStart_time()); 
//		Time oraFine = Time.valueOf(prenotation.getEnd_time()); 
//		List<Prenotazione> prenotazioni = repo.findByVeicoloIdAndDataAndOra(veicolo_id, data, oraInizio, oraFine);
//		System.out.println(prenotazioni);
//		
//	if(prenotazioni==null) {
//		return false;	
//			}
//		else 
//					return true; 
//		
//	}



}
