package it.gruppo2.sharing.entities;


import java.sql.Date;
import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="prenotazioni")
public class Prenotazione {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_prenotazione; 
	private int id_utente; 
	private int id_veicolo;
	private Date data_inizio;
	private Date data_fine;
	private Time ora_inizio; 
	private Time ora_fine; 
	private boolean status=true; 
	private double prezzo; 
	private long durata; 
	
	
	
	
	public Date getData_fine() {
		return data_fine;
	}
	public void setData_fine(Date data_fine) {
		this.data_fine = data_fine;
	}
	public Time getOra_inizio() {
		return ora_inizio;
	}
	public void setOra_inizio(Time oraInizio) {
		this.ora_inizio = oraInizio;
	}
	public Time getOra_fine() {
		return ora_fine;
	}
	public void setOra_fine(Time oraFine) {
		this.ora_fine = oraFine;
	}
	public int getId_prenotazione() {
		return id_prenotazione;
	}
	public void setId_prenotazione(int id_prenotazione) {
		this.id_prenotazione = id_prenotazione;
	}
	public int getId_utente() {
		return id_utente;
	}
	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	public int getId_veicolo() {
		return id_veicolo;
	}
	public void setId_veicolo(int id_veicolo) {
		this.id_veicolo = id_veicolo;
	}
	public Date getData_inizio() {
		return data_inizio;
	}
	public void setData_inizio(Date data) {
		this.data_inizio = data;
	}
	@Override
	public String toString() {
		return "Prenotazione [id_prenotazione=" + id_prenotazione + ", id_utente=" + id_utente + ", id_veicolo="
				+ id_veicolo + ", data=" + data_inizio + ", ora_inizio=" + ora_inizio + ", ora_fine=" + ora_fine + "]";
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	public long getDurata() {
		return durata;
	}
	public void setDurata(long durata) {
		this.durata = durata;
	}
	

	



	
	
	
}
