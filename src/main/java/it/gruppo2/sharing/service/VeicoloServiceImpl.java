package it.gruppo2.sharing.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.gruppo2.sharing.entities.Veicolo;
import it.gruppo2.sharing.repos.VeicoloDAO;
import it.gruppo2.sharing.util.FileUploadUtil;

@Service
public class VeicoloServiceImpl implements VeicoloService {
	
	public static String percorsoImg = "Immagini";

	@Autowired
	VeicoloDAO repo; 
	
	@Override
	public Veicolo trovaUno(int id) {
		return repo.findById(id).get();
	}
	
	@Override
	public List<Veicolo> trovaTutti() {
		return repo.findAll();
	}

	@Override
	public Veicolo aggiungiVeicolo(Veicolo veicolo, MultipartFile main) {
			String fileName = "main.jpeg";
			Veicolo veicoloSalvato = repo.save(veicolo);
			String uploadDir = percorsoImg + "/" + (veicolo.getId_veicolo());
			veicolo.setImmagine("/" + uploadDir);
			repo.save(veicolo);
			if(!main.isEmpty()) {
				try {
					FileUploadUtil.saveFile(uploadDir, fileName, main);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return veicoloSalvato;
		}

	@Override
	public List<String> cercaTipologie() {
		return repo.trovaTipologie();
	}

	@Override
	public List<String> cercaAlimentazioni() {
		return repo.trovaAlimentazioni();
	}
	
	public List<Veicolo> filtra(List<Veicolo> veicoli, String alimentazione, String tipologia) {
	
		List<Veicolo> veicoliFiltrati = new ArrayList<>(); 
		
		for (Veicolo veicolo : veicoli) {
			
			if(veicolo.isDisponibilita()) {
				if(tipologia.equals("all") && alimentazione.equals("all")) {
					veicoliFiltrati.add(veicolo);
				}
				else if(tipologia.equals(veicolo.getTipologia()) && alimentazione.equals("all")) {
					veicoliFiltrati.add(veicolo);
				}
				else if(tipologia.equals("all") && alimentazione.equals(veicolo.getAlimentazione())) {
					veicoliFiltrati.add(veicolo);
				}
				else if(tipologia.equals(veicolo.getTipologia()) && alimentazione.equals(veicolo.getAlimentazione())) {
					veicoliFiltrati.add(veicolo);
				}
			}
		}
		return veicoliFiltrati;
	}

	@Override
	public void eliminaVeicolo(int id) {
		File temp = new File(repo.getById(id).getImmagine());
		try {
			FileUtils.deleteDirectory(temp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		repo.deleteById(id);
	}

	@Override
	public List<String> listaTipologie() {
		return repo.tipologie();
	}

	@Override
	public List<String> listaAlimentazioni() {
		return repo.alimentazioni();
	}

	@Override
	public void setDisponibilita(Veicolo veicolo) {
		if(veicolo.isDisponibilita() == true) {
			veicolo.setDisponibilita(false);
		}
		else {
			veicolo.setDisponibilita(true);
		}
		repo.save(veicolo); 
	}

	@Override
	public List<Veicolo> impagina(List<Veicolo> veicoli, int numPagina, int dimLista) {
		int offset = numPagina * dimLista;
		List<Veicolo> impaginati = new ArrayList<Veicolo>();
		for (int indice = 0; indice < dimLista; indice++) {
			if(indice < veicoli.size()) {
				impaginati.add(veicoli.get(indice + offset)) ;
			}
		}
		return impaginati;
	}
	
	@Override
	public List<String> filtraAlimentazioni(String tipologia){
		return repo.filtraAlimentazioni(tipologia);
	}
	
	
}
