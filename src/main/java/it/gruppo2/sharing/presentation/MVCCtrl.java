package it.gruppo2.sharing.presentation;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import it.gruppo2.sharing.entities.Prenotazione;
import it.gruppo2.sharing.entities.Utente;
import it.gruppo2.sharing.entities.Veicolo;
import it.gruppo2.sharing.model.ModelloUtente;
import it.gruppo2.sharing.model.Prenotation;
import it.gruppo2.sharing.service.PrenotazioneService;
import it.gruppo2.sharing.service.UtenteService;
import it.gruppo2.sharing.service.VeicoloService;

@Controller
@RequestMapping("")
@SessionAttributes("utente")
public class MVCCtrl {

	@Autowired
	private VeicoloService veicoloService;

	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private PrenotazioneService prenotazioneService; 
	
	
	double TARIFFA= 0.15; 
	
//	int id_veicoloDaPrenotare; 

	//----------------------------------------------------------------------------------------------------------------------------------Rotte
	//Rotta principale, conduce alla homepage
	@RequestMapping("")
	public String home(Veicolo veicolo, Model model, Utente utente) {
		//Passo al filtro i dati delle tipologie ed alimentazioni solo dei veicoli presenti e disponibili
		model.addAttribute("tipologie", veicoloService.cercaTipologie());
		model.addAttribute("alimentazioni", veicoloService.cercaAlimentazioni());
		return "index";
	}
	
	//Nel caso si inserisse manualmente /login nella barra degli indirizzi, riconduce alla home
	@GetMapping("/login")
	public String falseLogin() {
		return "redirect:/";
	}
	
	//Rotta di login, usata in seguito alla compilazione ed alla conferma della modale aperta dal pulsante "Login"
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
		//Ricerca utente
		Utente utente = utenteService.trovaByEmailAndPasword(email, password);
		//Se l'utente non esiste rimanda ad una pagina di errore
		if(utente == null) {
			return "loginError";
		}
		model.addAttribute("utente", utente);
		return "redirect:/profilo";
	}

	//Rotta di logout, usata in seguito alla pressione del pulsante "Logout", visibile solo se loggati
	@RequestMapping("/logout")
	public String logout(SessionStatus sessionStatus, Utente utente, Model model) {
		//Controllo che la richiesta di logout sia effettivamente di un utente registrato, altrimenti rimando alla home
		if(utente.getTipo() != 'A' && utente.getTipo() != 'B') {
			return "redirect:/";
		}
		//Chiudo la sessione dell'utente
		sessionStatus.setComplete();
		//A causa di un mancato aggiornamento dell'header, per il corretto funzionamento devo impostare l'id a 0
		utente.setId_utente(0);
		model.addAttribute("utente", utente);
		return "logout";
	}
	
	//Rotta della lista veicoli
	@RequestMapping("/elenco/{pagina}/")
	public String filtra(@RequestParam("alimentazione") String alimentazione, @RequestParam("tipologia") String tipologia, @PathVariable("pagina") int pagina, Model model, Utente utente) {
		//Filtro la lista dei veicoli da visualizzare, se utilizzo il filtro della home, altrimenti visualizzo tutti i veicoli disponibili
		List<Veicolo> veicoli = veicoloService.filtra(veicoloService.trovaTutti(), alimentazione, tipologia);
		List<Veicolo> impaginati = veicoloService.impagina(veicoli, pagina, 10);
		int numPagine = (int)Math.ceil((double)veicoli.size() / 10);
		model.addAttribute("numPagine", numPagine);
		model.addAttribute("alimentazione", alimentazione);
		model.addAttribute("tipologia", tipologia);
		model.addAttribute("tipologie", veicoloService.cercaTipologie());
		model.addAttribute("alimentazioni", veicoloService.cercaAlimentazioni());
		model.addAttribute("tutti", veicoloService.trovaTutti());
		model.addAttribute("veicoli", impaginati);
		model.addAttribute("precedente", "/elenco/" + (pagina-1) + "/?tipologia=" + tipologia +"&alimentazione=" + alimentazione);
		model.addAttribute("successiva", "/elenco/" + (pagina+1) + "/?tipologia=" + tipologia +"&alimentazione=" + alimentazione);
		return "listaVeicoli";
	}
	
	//Rotta pagina contatti
	@RequestMapping("/contatti")
	public String contatti(Utente utente) {
		return "contatti";
	}
	
	//Rotta della pagina del dettaglio veicolo, aperta dal pulsante "Scopri" nella lista veicoli
	@RequestMapping("/dettaglio/{id}")
	public String vediDettaglio( Model model, Utente utente,  @PathVariable("id") int id) {
		boolean tipo = true; 
		Veicolo veicolo = veicoloService.trovaUno(id);
//		id_veicoloDaPrenotare = id;
		if(prenotazioneService.checkPrenotazione(utente.getId_utente()) == true) {
			model.addAttribute("errore", true);
		}
		model.addAttribute("veicolo", veicolo);
		model.addAttribute("utente", utente);
		if(utente.getTipo() !='B') {
			tipo = false; 
		}
		model.addAttribute("tipo", tipo); 
		
		System.out.println(model.toString());
		return "dettaglioVeicolo";
	}
	
	//----------------------------------------------------------------------------------------------------------------------------------
	
	//----------------------------------------------------------------------------------------------------------------------------------Sezione utente
	
	//Rotta di reindirizzamento: controlla il tipo di utente e lo reinvia al profilo corretto, oppure alla home
	@RequestMapping("/profilo")
	public String apriProfilo(Utente utente) {
		if(utente.getTipo() == 'A') {
			return "redirect:/admin";
		}
		else if(utente.getTipo() == 'B') {
			return "redirect:/user";
		}
		else return "redirect:/";
	}
	
	@RequestMapping("/profilo/gestisci/{id}")
	public String gestisciUtente(@PathVariable("id") int id, Model model, Utente utente) {
		// Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
		if (utente.getId_utente() != id) {
			return "redirect:/profilo";
		}
		return "gestioneUtenti";
	}
	
	@RequestMapping("/profilo/gestisci/conferma")
	public String confermaUtente(Utente utente, Model model, SessionStatus sessionStatus) {
		utenteService.gestisciUtente(utente);
		sessionStatus.setComplete();
//		Utente utenteSalvato = utenteService.trovaByEmailAndPasword(utente.getEmail(), utente.getPassword());
//		model.addAttribute("utente", utenteSalvato);
		return "redirect:/login";
	}
	
	
	
	
	@RequestMapping("/disponibilita/{id}")
	public String cambiaDisponibilita(@PathVariable("id") int id ) {
		veicoloService.setDisponibilita(veicoloService.trovaUno(id));
		return "adminListaVeicoli";
	}
	
	
	//.....................................................................................................................User
	
	//Rotta della pagina utente
	@RequestMapping("/user")
	public String paginaUtente(Utente utente, Model model) {
		//Controllo che ad aver richiamato la rotta sia effettivamente un utente
		if(utente.getTipo()!= 'B') {
			return "redirect:/profilo";
		}
		//Preparo una lista delle prenotazioni dell'utente passate
		
		
		List<Prenotazione> attive = prenotazioneService.findById_utente(utente.getId_utente()); 
		
		model.addAttribute("prenotazioni", attive); 
		model.addAttribute("utente", utente);
		
		model.addAttribute("veicoli", veicoloService);
		return "userLogin";
	}
	
	@RequestMapping("/storico")
	public String storicoUtente(Utente utente , Model model) {
		if(utente.getTipo()!= 'B') {
			return "redirect:/profilo";
		}
		
		
		List<Prenotazione> storico = prenotazioneService.trovaStorico(utente.getId_utente()); 
		model.addAttribute("prenotazioni",storico);
		model.addAttribute("veicoli", veicoloService);
		
		System.out.println("utente cognome : " + utente.getCognome());
		System.out.println("utente id : " + utente.getId_utente());
		return "userLogin1";
	}
	
	
	//.....................................................................................................................
	//.....................................................................................................................Admin
	
	//Rotta pagina amministratore
	@RequestMapping("/admin")
	public String paginaAdmin(Model model, Utente utente) {
		//Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
		if(utente.getTipo()!= 'A') {
			return "redirect:/profilo";
		}
		return "adminLogin";
	}

	//Rotta lista veicoli dell'amministratore
	@RequestMapping("/admin/veicoli/{pagina}")
	public String amministraVeicoli(Model model, Utente utente, @PathVariable("pagina") int pagina) {
		//Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
		if(utente.getTipo()!= 'A') {
			return "redirect:/profilo";
		}
		int numPagine = (int)Math.ceil((double)veicoloService.trovaTutti().size() / 10);
		model.addAttribute("numPagine", numPagine);
		model.addAttribute("veicoli", veicoloService.impagina(veicoloService.trovaTutti(), pagina, 10));
		return "adminListaVeicoli";
	}

	//Rotta di eliminazione veicolo dato l'id
	@GetMapping("/admin/veicoli/elimina/{id}")
	public String eliminaVeicolo(@PathVariable("id") int id, Utente utente){
		//Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
		if(utente.getTipo()!= 'A') {
			return "redirect:/profilo";
		}
		//Elimino veicolo dal DB e files sul server (cartella con immagini)
		veicoloService.eliminaVeicolo(id);
		return "redirect:/admin/veicoli/0";
	}
	
	//Rotta di aggiunta o modifica veicolo dato l'id
	@GetMapping("/admin/veicoli/gestisci/{id}")
	public String modificaVeicolo(@PathVariable("id") int id, Model model, Utente utente) {
		//Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
		if(utente.getTipo()!= 'A') {
			return "redirect:/profilo";
		}
		//Controllo l'id del veicolo: se e' 0 si tratta di un nuovo veicolo
		if(id == 0) {
			model.addAttribute("veicolo", new Veicolo());
		}
		//Altrimenti se e' diverso da 0 sto modificando un veicolo presente nel db
		else {
			model.addAttribute("veicolo", veicoloService.trovaUno(id));
		}
		model.addAttribute("id_utente", utente.getId_utente());
		//Utilizzo due DB in cui sono elencate tutte le tipologie di veicoli e le possibili alimentazioni
		model.addAttribute("tipologie", veicoloService.listaTipologie());
		model.addAttribute("alimentazioni", veicoloService.listaAlimentazioni());
		return "adminGestisciVeicolo";
	}
	
	//Nel caso si inserisse manualmente /admin/veicoli/aggiungi nella barra degli indirizzi
	@GetMapping("/admin/veicoli/aggiungi")
	public String falseSave() {
		return "redirect:/profilo";
	}
	
	//Rotta di invio dati al server
	@PostMapping("/admin/veicoli/aggiungi")
	public String salvaVeicolo(Veicolo veicolo, @RequestParam("main") MultipartFile main, @RequestParam("quantita") int quantita, Model model, Utente utente) {
		//Per permettere l'inserimento di piu' di un veicolo alla volta (es 10 automobili) utilizzo un ciclo che clona il veicolo
		for(int i = quantita; i > 0; i--) {
			try {
				veicoloService.aggiungiVeicolo((Veicolo)veicolo.clone(), main);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		//Rimando alla lista dei veicoli
		return "redirect:/admin/veicoli/0";
	}
	
	@RequestMapping("/admin/utenti/{pagina}")
	public String amministraUtenti(Model model, Utente utente, @PathVariable("pagina") int pagina) {
		//Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
		if(utente.getTipo()!= 'A') {
			return "redirect:/profilo";
		}
		int numPagine = (int)Math.ceil((double)utenteService.trovaUtenti('B').size() / 10);
		model.addAttribute("numPagine", numPagine);
		model.addAttribute("utenti", utenteService.impagina(utenteService.trovaUtenti('B'), pagina, 10));
		return "adminListaUtenti";
	}
	
	
	
//----- Lista Admin prenotazioni ----------------------
	@RequestMapping("/admin/prenotazioni/{pagina}")
	public String amministraPrernotazioni(Model model, Utente utente, @PathVariable("pagina") int pagina) {
		//Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
		if(utente.getTipo()!= 'A') {
			return "redirect:/profilo";
		}
		int numPagine = (int)Math.ceil((double)prenotazioneService.torvaTutte().size() / 10);
		model.addAttribute("numPagine", numPagine);
		model.addAttribute("prenotazioni", prenotazioneService.torvaTutte()); 
		return "adminListaPrenotazioni";
	}


	
	
	//Rotta di eliminazione utente dato l'id
		@GetMapping("/admin/utenti/elimina/{id}")
		public String eliminaUtente(@PathVariable("id") int id, Utente utente){
			//Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
			if(utente.getTipo()!= 'A') {
				return "redirect:/profilo";
			}
			//Elimino veicolo dal DB e files sul server (cartella con immagini)
			utenteService.eliminaUtente(id);
			return "redirect:/admin/utenti/0";
		}
		
		//Rotta modifica utente dato l'id
		@GetMapping("/admin/utenti/gestisci/{id}")
		public String modificaUtente(@PathVariable("id") int id, Model model, Utente utente) {
			//Controllo che ad aver richiamato la rotta sia effettivamente un amministratore
			if(utente.getTipo()!= 'A') {
				return "redirect:/profilo";
			}
			//Controllo l'id del veicolo: se e' 0 si tratta di un nuovo veicolo
			if(id == 0) {
				model.addAttribute("veicolo", new Veicolo());
			}
			//Altrimenti se e' diverso da 0 sto modificando un veicolo presente nel db
			else {
				model.addAttribute("veicolo", veicoloService.trovaUno(id));
			}
			model.addAttribute("id_utente", utente.getId_utente());
			//Utilizzo due DB in cui sono elencate tutte le tipologie di veicoli e le possibili alimentazioni
			model.addAttribute("tipologie", veicoloService.listaTipologie());
			model.addAttribute("alimentazioni", veicoloService.listaAlimentazioni());
			return "adminGestisciVeicolo";
		}
		

	
	//.....................................................................................................................

//	@RequestMapping("/addPrenotazione")
//	public String addPrenottazione(Utente utente){
//		if(utente.getTipo()!='B') {
//			return "redirect:/"; 
//		}
//		return "dettaglioVeicolo";
//	}
	
//	@PostMapping("/prenotazione")
//	public String prenotazione( Utente utente, Prenotation prenotation,  @RequestParam("veicolo_id") int veicolo_id,  Model model){
//		if(utente.getTipo()!='B') {
//			return "redirect:/"; 
//		}
//		System.out.println("prenotazione: " + prenotation.toString());
//		
//		System.out.println("veicolo ID: "+ veicolo_id);
//		Prenotazione prenotazione = new Prenotazione(); 
//		Veicolo veicolo = veicoloService.(veicolo_id);
//		
//		boolean controlloDate = prenotazioneService.controllaDate(prenotation,veicolo_id); 
//		if (veicolo.isDisponibilita() == true && controlloDate ==false) {
//			
//			prenotazione.setOraInizio(Time.valueOf(prenotation.getStart_time())); 
//			prenotazione.setOraFine(Time.valueOf(prenotation.getEnd_time())); 
//			prenotazione.setData(Date.valueOf(prenotation.getStart_date()));
//			prenotazione.setId_utente(utente.getId_utente()); 
//			prenotazione.setId_veicolo(veicolo_id); 
//			prenotazioneService.addPrenotazione(prenotazione); 
////			veicoloService.setDisponibilita(veicolo);
//			System.out.println("dsiponibilita: " + veicoloService.trovaUno(veicolo_id).isDisponibilita());
//		} 
//		else {
//			model.addAttribute("errore", "Veicolo Non Disponibile");
//		}
//		return "redirect:/user";
	//}
	//.....................................................................................................................

	
	@PostMapping("/start/prenotazione")
	public String prenotazione(Utente utente, Prenotation prenotation, Model model, @RequestParam("veicolo_id")int veicolo_id) {
		if(utente.getTipo()!='B') {
			return "redirect:/profilo"; 
		}
		Prenotazione prenotazione =  new Prenotazione();
			prenotazione.setId_utente(utente.getId_utente()); 
			prenotazione.setId_veicolo(veicolo_id);
//			prenotazione.setData_inizio(Date.valueOf(prenotation.getStart_date())); 
//			prenotazione.setOra_inizio(Time.valueOf(prenotation.getStart_time())); 
			
			prenotazione.setData_inizio(Date.valueOf(LocalDate.now())); 
			prenotazione.setOra_inizio(Time.valueOf(LocalTime.now())); 
		
			
			prenotazioneService.addPrenotazione(prenotazione);
			veicoloService.setDisponibilita(veicoloService.trovaUno(veicolo_id)); 
			
			model.addAttribute("veicolo", veicoloService.trovaUno(veicolo_id)); 
			model.addAttribute("prenotazione", prenotazione); 
			return "redirect:/profilo";
		
	}
	
//----------- fine prenotazione
	@GetMapping("/end/prenotazione/{id_prenotazione}")
	public String chiusuraPrenotazione(@PathVariable("id_prenotazione") int id_prenotazione,
			Model model, Utente utente) {
		if(utente.getTipo()!='B' && utente.getTipo()!='A' ) {
			return "redirect:/profilo"; 
		}
		
		Prenotazione prenotazione = prenotazioneService.trovaUna(id_prenotazione); 
		prenotazione.setOra_fine(Time.valueOf(LocalTime.now())); 
		prenotazione.setData_fine(Date.valueOf(LocalDate.now()));
		prenotazione.setStatus(false); 
			
		veicoloService.setDisponibilita(veicoloService.trovaUno(prenotazione.getId_veicolo())); 
		
	 
		LocalDateTime fine =LocalDateTime.of(LocalDate.now(), LocalTime.now()); 
		LocalDateTime inizio = LocalDateTime.of(prenotazione.getData_inizio().toLocalDate(), prenotazione.getOra_inizio().toLocalTime()); 
		
		long durata = Math.abs(Duration.between(fine, inizio).toMinutes());
		
		double prezzo = TARIFFA*durata;
		
		System.out.println("prezzo: " + prezzo);
		System.out.println("durata: " + durata + "min");
	
		
		prenotazione.setDurata(durata); 
		prenotazione.setPrezzo(prezzo); 
		
		prenotazioneService.addPrenotazione(prenotazione);
		model.addAttribute("prenotazione", prenotazione); 
		
		
		return"redirect:/profilo"; 
	}
	
	//-------- eliminare una prenotazione --------------- 
	@GetMapping("/admin/elimina/prenotazione/{id_prenotazione}")
	public String eliminaPrenotazione(@PathVariable("id_prenotazione") int id_prenotazione, Utente utente, Model model) {
		if(utente.getTipo()!='A') {
			return "redirect:/profilo"; 
		}
		
		
		
		
		Veicolo veicolo = veicoloService.trovaUno(prenotazioneService.trovaUna(id_prenotazione).getId_veicolo());
		Prenotazione prenotazione =  prenotazioneService.trovaUna(id_prenotazione); 
		if(prenotazione.isStatus()) {
			prenotazione.setOra_fine(Time.valueOf(LocalTime.now())); 
			prenotazione.setData_fine(Date.valueOf(LocalDate.now()));
			prenotazione.setStatus(false); 
			veicoloService.setDisponibilita(veicolo); 
			prenotazioneService.elimina(id_prenotazione);
		} else {
			prenotazioneService.elimina(id_prenotazione);
		}
		
		
		
		
		return"redirect:/admin/prenotazioni/0"; 
	}
	
	@GetMapping("/dettaglio/prenotazione/{id_prenotazione}")
	public String dettaglioPrenotazione(@PathVariable("id_prenotazione") int id_prenotazione) {
		
		return""; 
		
		
	}
	
//------ chiama il form di registrazione dell'utente ---------------------------------------------------
	@GetMapping("/registrazione")
	public String registrazione(Utente utente) {
		return "registrazione"; 
	}

// ------- Utente viene registrato dentro il sistema -----------------------------------------------------
	@PostMapping("/aggiungi/utente") 
	public String aggiungi(Model model,  ModelloUtente modelUtente, Utente utente){
		
		Utente utenteRegistrato =  new Utente(); 
		boolean errore = false; 
		
			if( utenteService.checkMail(modelUtente.getEmail())) {
				
			
			utenteRegistrato.setCognome(modelUtente.getCognome());
			utenteRegistrato.setNome(modelUtente.getNome());
			utenteRegistrato.setEmail(modelUtente.getEmail());
			utenteRegistrato.setTipo('B');
			utenteRegistrato.setData_nascita(Date.valueOf(modelUtente.getDataNascita())); 
			utenteRegistrato.setPassword(modelUtente.getPassword()); 
			
			utenteService.aggiungiUtente(utenteRegistrato); 
		} else {
			errore = true; 
			 
		}
			model.addAttribute("errore", errore);
		
		
		return "confermaProfiloCreato"; 
	}
	
//	------------- admin modifica dati dell'utente FATTO DA FRANKY -----------------------------
	

//	@GetMapping("/admin/utenti/gestisci/{id}")
//	public String modificaAdmin(Utente utente) {
//		if(utente.getTipo()!='A') {
//			return "redirect:/profilo"; 
//		}
//		return "modificaUtente"; 
//	}
//	
//	
//	@PostMapping("/admin/modifica/utente/{id_utente}")
//	public String adminModificaUtente(@PathVariable ("id_utente") int id_utente,  Utente utente, Model model, ModelloUtente modelloUtente) {
//		if(utente.getTipo()!='A') {
//			return "redirect:/profilo"; 
//		}
//		utenteService.modificaUtente(id_utente, modelloUtente);
//		
//		
//		return "redirect:/profilo";
//	}
//	
	
	
// ----------- Utente modifica i suoi dati --------------------
	
	@GetMapping("/user/modifica")
	public String modifica(Utente utente) {
		if(utente.getTipo()!='B') {
			return "redirect:/profilo"; 
		}
		return "modificaUtente"; 
	}
	
	
	@PostMapping("/modifica/utente")
	public String modificaUtente(Utente utente, ModelloUtente modelloUtente) {
		if(utente.getTipo()!='B') {
			return "redirect:/profilo"; 
		}
		
		utenteService.modificaUtente(utente.getId_utente(), modelloUtente);
		
		return "redirect:/profilo"; 
	}
	
	
}