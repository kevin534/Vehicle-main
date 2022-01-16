package com.generation.fileupload.mvcctrl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.generation.fileupload.config.CustomProperties;
import com.generation.fileupload.entity.Veicolo;
import com.generation.fileupload.repo.IVeicoloRepo;
import com.generation.fileupload.service.VeicoloService;
import com.generation.fileupload.util.FileUploadUtil;



@Controller
@RequestMapping("/mvc/veicolo")
public class VicoloCtrl {

	@Autowired
    private VeicoloService _vserv;
	
	@PostMapping("/save")
	public String salvaVeicolo(Veicolo veicolo, @RequestParam("image") MultipartFile multipartFile, Model model) {
		
		// salvo il veicolo su database, e l'immagine in una cartella
		Veicolo veicoloSalvato = _vserv.saveVeicolo(veicolo, multipartFile);
        
        // si può fare lo stesso percorso con un'api rest, e restituire solo il veicolo senza l'html
        model.addAttribute("veicolo", veicoloSalvato);
        
		return "file-salvato";
	}
	
}
