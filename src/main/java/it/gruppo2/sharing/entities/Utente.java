package it.gruppo2.sharing.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name ="archivio_utenti")
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_utente; 
	private String nome;
	private String cognome;
	
	private Date data_nascita;
	private String email;
	private String password;
	private char tipo; 
	private LocalDateTime ultima_modifica; 
	private LocalDateTime data_iscrizione;
	
	
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public Date getData_nascita() {
		return this.data_nascita;
	}
	
	public void setData_nascita(Date data_nascita) {
		this.data_nascita = data_nascita;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public char getTipo() {
		return tipo;
	}
	
	public void setTipo(char tipo) {
		this.tipo = tipo;
	}
	
	public int getId_utente() {
		return id_utente;
	}
	
	public LocalDateTime getUltimamodifica() {
		return ultima_modifica;
	}
	
	public LocalDateTime getDataiscrizione() {
		return data_iscrizione;
	}

	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}

	@Override
	public String toString() {
		return "Utente [id_utente=" + id_utente + ", nome=" + nome + ", cognome=" + cognome + ", data_nascita="
				+ data_nascita + ", email=" + email + ", password=" + password + ", tipo=" + tipo + ", ultima_modifica="
				+ ultima_modifica + ", data_iscrizione=" + data_iscrizione + "]";
	}
}
