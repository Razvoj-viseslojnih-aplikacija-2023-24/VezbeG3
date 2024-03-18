package rva.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import rva.models.Artikl;
import rva.models.StavkaPorudzbine;
import rva.services.ArtiklService;
import rva.services.StavkaPorudzbineService;

@RestController
public class StavkaPorudzbineController {
	
	@Autowired
	private StavkaPorudzbineService service;
	
	@Autowired
	private ArtiklService artiklService;

	@GetMapping("/stavka-porudzbine/artikl/{foreignKey}")
	public ResponseEntity<?> getStavkeByArtikl(@PathVariable int foreignKey){

		Optional<Artikl> artikl = artiklService.findById(foreignKey);
		if(artikl.isPresent()) {
			List<StavkaPorudzbine> stavke = service.findByForeignKey(artikl.get());
			if(!stavke.isEmpty()) {
				return ResponseEntity.ok(stavke);
			}else {
				return ResponseEntity.status(404).body("Resources with requested "
						+ "foreign key: " + foreignKey + " do not exist");
			}
		}
		return ResponseEntity.status(404).body("Foreign key: " + foreignKey+ " is invalid");
	}
}
