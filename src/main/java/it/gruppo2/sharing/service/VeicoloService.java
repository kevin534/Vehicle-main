package it.gruppo2.sharing.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.gruppo2.sharing.entities.Prenotazione;
import it.gruppo2.sharing.entities.Veicolo;

public interface VeicoloService {
	List<Veicolo> trovaTutti();
	Veicolo trovaUno(int id);
	Veicolo aggiungiVeicolo(Veicolo veicolo, MultipartFile multipartFile);
	List<String> cercaTipologie();
	List<String> cercaAlimentazioni();
	List<Veicolo> filtra(List<Veicolo> veicoli, String alimentazione, String tipologia); 
	void eliminaVeicolo(int id);
	List<String> listaTipologie();
	List<String> listaAlimentazioni();
	void setDisponibilita(Veicolo v); 
	List<Veicolo> impagina(List<Veicolo> veicoli, int numPagina, int dimLista);
	List<String> filtraAlimentazioni(String tipologia);
}
