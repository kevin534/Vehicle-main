package it.gruppo2.sharing.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.gruppo2.sharing.entities.Utente;

public interface UtenteDAO extends JpaRepository<Utente, Integer> {
	Utente findByEmail(String email);
	Utente findByEmailAndPassword(String email, String password);
	List<Utente> findByTipo(char tipo);
}
