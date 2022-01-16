package it.gruppo2.sharing.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Prenotation {
	
	

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate start_date; 
	
	@DateTimeFormat(pattern = "HH:mm") // sempre in maiuscolo HH
	private LocalTime start_time; 

	
	private Long nCarta;
	
	
	
	 
	
	
	public LocalTime getStart_time() {
		return start_time;
	}
	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}


	public LocalDate getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}
	
	
	public Long getnCarta() {
		return nCarta;
	}
	public void setnCarta(Long nCarta) {
		this.nCarta = nCarta;
	}
	
	
	
	
	

}
