package it.gruppo2.sharing.repos;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.gruppo2.sharing.entities.Prenotazione;

public interface PrenotazioneDAO extends JpaRepository<Prenotazione, Integer> {

	@Query(value="select * from prenotazioni where id_utente=? and status = true", nativeQuery = true)
	List<Prenotazione> findById_Utente(int idUtente);
	@Query(value="select * from prenotazioni where id_veicolo=? and data=? and ora_fine <= ? and ora_inizio >=?", nativeQuery = true)
	List<Prenotazione> findByVeicoloIdAndDataAndOra(int veicolo_id, Date data, Time oraInizio, Time oraFine);
	


	}
