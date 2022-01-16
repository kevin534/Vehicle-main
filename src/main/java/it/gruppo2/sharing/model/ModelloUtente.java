package it.gruppo2.sharing.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ModelloUtente {
	
	private  String email;
	private  String nome;
	private  String cognome;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private  LocalDate dataNascita; 
	private  String password;
	private  String confermaPassword;
	
	
	
	
	public LocalDate getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
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
	public String getConfermaPassword() {
		return confermaPassword;
	}
	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	} 

	
	
	
	
}
