package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Artikl;
import rva.models.Porudzbina;
import rva.models.StavkaPorudzbine;

@Repository
public interface StavkaPorudzbineRepository extends JpaRepository<StavkaPorudzbine, Integer> {

	List<StavkaPorudzbine> findByCenaGreaterThanOrderByCenaAsc(double cena);
	
	List<StavkaPorudzbine> findByPorudzbina(Porudzbina porudzbina);
	List<StavkaPorudzbine> findByArtikl(Artikl artikl);
}
