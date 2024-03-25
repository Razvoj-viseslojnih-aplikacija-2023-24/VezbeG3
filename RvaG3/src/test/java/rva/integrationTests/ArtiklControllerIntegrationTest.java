package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import rva.models.Artikl;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArtiklControllerIntegrationTest {
	
	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Artikl>> response = template.exchange(
				"/artikl",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Artikl>> () {} );
		
		ArrayList<Artikl> list = (ArrayList<Artikl>)response.getBody();
		for(int i = 0; i < list.size(); i ++) {
			if(highestId <= list.get(i).getId()) {
				highestId = list.get(i).getId() + 1;
			}
		}
	}
	
	void getHighestId() {
		createHighestId();
		highestId--;
	}

	@Test
	@Order(1)
	void testGetAllArtikls() {
		//Postavka
		ResponseEntity<List<Artikl>> response = template.exchange(
				"/artikl",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Artikl>> () {} );
		
		int statusCode = response.getStatusCode().value();
		List<Artikl> artikli = response.getBody();
		
		//Test
		assertEquals(200, statusCode);
		assertTrue(!artikli.isEmpty());
		
	}
	
	@Test
	@Order(2)
	void testGetArtiklById() {
		int id = 1;
		ResponseEntity<Artikl> response = template
				.getForEntity("/artikl/id/" + id, Artikl.class);
		
		int statusCode = response.getStatusCode().value();
		Artikl artikl = response.getBody();
		
		assertEquals(200,statusCode);
		assertNotNull(artikl);
		assertEquals(id, artikl.getId());
	}
	
	@Test
	@Order(3)
	void testGetArtiklsByNaziv() {
		String naziv = "Moja";
		ResponseEntity<List<Artikl>> response = template
				.exchange("/artikl/naziv/"+naziv, HttpMethod.GET,
						null,
						new ParameterizedTypeReference<List<Artikl>> () {});
		
		int statusCode = response.getStatusCode().value();
		List<Artikl> artikli = response.getBody();
		String nazivArtikla = artikli.get(0).getNaziv();
		
		assertEquals(200,statusCode);
		assertTrue(!artikli.isEmpty());
		//assertTrue(nazivArtikla.startsWith(naziv));
		for(Artikl a: artikli) {
			assertTrue(a.getNaziv().startsWith(naziv));
			//U slucaju brojcane vrednosti
			// asertTrue(a.getCena() > 200)
			//U slucaju boolean vrednosti
			// asertTrue(a.getBoolean());
		}
	}
	
	@Test
	@Order(4)
	void testCreateArtikl() {
		Artikl artikl = new Artikl();
		artikl.setNaziv("POST test");
		artikl.setProizvodjac("POST proizvodjac");
		
		HttpEntity<Artikl> entity = new HttpEntity<Artikl>(artikl);
		createHighestId();
		
		ResponseEntity<Artikl> response = template
				.exchange("/artikl", HttpMethod.POST,
						entity, Artikl.class);
		
		int statusCode = response.getStatusCode().value();
		Artikl createdArtikl = response.getBody();
		
		assertEquals(201, statusCode);
		assertEquals(highestId, createdArtikl.getId());
		assertEquals(artikl.getNaziv(), createdArtikl.getNaziv());
		assertEquals(artikl.getProizvodjac(), createdArtikl.getProizvodjac());
		assertEquals("/artikl/" + highestId, response.getHeaders().getLocation().getPath());
	}
	
	@Test
	@Order(5)
	void testUpdateArtikl() {
		Artikl artikl = new Artikl();
		artikl.setNaziv("PUT test");
		artikl.setProizvodjac("PUT proizvodjac");
		
		//AKO IMATE DATUM KAO FIELD
		//Date datum = new Date();
		//artikl.setDatum(datum);
		
		HttpEntity<Artikl> entity = new HttpEntity<Artikl>(artikl);
		getHighestId();
		
		ResponseEntity<Artikl> response = template
				.exchange("/artikl/id/" + highestId, HttpMethod.PUT,
						entity, Artikl.class);
		
		int statusCode = response.getStatusCode().value();
		Artikl updatedArtikl = response.getBody();
		
		assertEquals(200, statusCode);
		assertEquals(highestId, updatedArtikl.getId());
		assertEquals(artikl.getNaziv(), updatedArtikl.getNaziv());
		assertEquals(artikl.getProizvodjac(), updatedArtikl.getProizvodjac());
	}
	
	
	@Test
	@Order(6)
	void testDeleteArtikl() {
		getHighestId();
		ResponseEntity<String> response = template
				.exchange("/artikl/id/" + highestId, HttpMethod.DELETE,
						null, String.class);
		
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted"));
	}
	
	
	

}
