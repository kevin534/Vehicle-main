package it.gruppo2.sharing.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="veicoli")
public class Veicolo implements Cloneable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_veicolo; 
	private String modello;
	private String descrizione; 
	private String tipologia; 
	private String alimentazione; 
	private String posizione;
	private boolean disponibilita; 
	private String immagine; 
	private int id_utente;

	public void setId_veicolo(int id_veicolo) {
		this.id_veicolo = id_veicolo;
	}

	public String getModello() {
		return modello;
	}
	
	public void setModello(String modello) {
		//modello = setMaiuscola(modello);
		this.modello = modello;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		
		this.descrizione = descrizione;
	}
	
	public String getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(String tipologia) {
		tipologia = setMaiuscola(tipologia);
		this.tipologia = tipologia;
	}
	
	public String getAlimentazione() {
		return alimentazione;
	}
	
	public void setAlimentazione(String alimentazione) {
		alimentazione = setMaiuscola(alimentazione);
		this.alimentazione = alimentazione;
	}
	
	public boolean isDisponibilita() {
		return disponibilita;
	}
	
	public void setDisponibilita(boolean disponibilita) {
		this.disponibilita = disponibilita;
	}
	
	public String getImmagine() {
		return immagine;
	}
	
	public void setImmagine(String immagine) {
		this.immagine = immagine;
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
	
	public String setMaiuscola(String stringa) {
		String[] parole = stringa.split(" ");
		stringa = "";
	    for(int i=0; i < parole.length; i++) {
	    	if(i > 0) {
				stringa += " ";
			}
	        if (!parole[i].isEmpty()) {
	        	stringa += parole[i].substring(0, 1).toUpperCase() + parole[i].substring(1).toLowerCase();
		    }
		}
		return stringa;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getPosizione() {
		return posizione;
	}

	public void setPosizione(String posizione) {
		this.posizione = posizione;
	}

	@Override
	public String toString() {
		return "Veicolo [id_veicolo=" + id_veicolo + ", modello=" + modello + ", descrizione=" + descrizione
				+ ", tipologia=" + tipologia + ", alimentazione=" + alimentazione + ", posizione=" + posizione
				+ ", disponibilita=" + disponibilita + ", immagine=" + immagine + ", id_utente=" + id_utente + "]";
	}

}