package it.gruppo2.sharing.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.gruppo2.sharing.entities.Veicolo;

public interface VeicoloDAO extends JpaRepository<Veicolo, Integer> {
	
	@Query(value = "SELECT DISTINCT tipologia FROM veicoli WHERE disponibilita = 1", nativeQuery = true)
	public List<String> trovaTipologie();
	@Query(value = "SELECT DISTINCT alimentazione FROM veicoli WHERE disponibilita = 1", nativeQuery = true)
	public List<String> trovaAlimentazioni();
	
	public List<String> findByAlimentazione(String alimentazione);
	
	@Query(value = "SELECT DISTINCT alimentazione FROM alimentazioni", nativeQuery = true)
	public List<String> alimentazioni();
	@Query(value = "SELECT DISTINCT tipologia FROM tipologie", nativeQuery = true)
	public List<String> tipologie();
	
	@Query(value = "SELECT DISTINCT alimentazione FROM veicoli WHERE disponibilita = 1 AND tipologia = ?", nativeQuery = true)
	public List<String> filtraAlimentazioni(String tipologia);
}
