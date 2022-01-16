package it.gruppo2.sharing.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gruppo2.sharing.service.VeicoloService;

@RestController
public class RESTCtrl {
	
	@Autowired
	private VeicoloService veicoloService;
	
	
	@GetMapping("/api/filtro/{tipologia}")
	public List<String> filtra(@PathVariable("tipologia") String tipologia){
		System.out.println(veicoloService.filtraAlimentazioni(tipologia).toString());
		return veicoloService.filtraAlimentazioni(tipologia);
		
	}

}
