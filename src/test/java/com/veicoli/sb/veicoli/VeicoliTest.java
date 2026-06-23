package com.veicoli.sb.veicoli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.veicoli.sb.dto.input.MacchinaReq;
import com.veicoli.sb.dto.output.VeicoliDTO;
import com.veicoli.sb.models.Categoria;
import com.veicoli.sb.models.TipoAlimentazione;
import com.veicoli.sb.repositories.ICategoriaRepository;
import com.veicoli.sb.repositories.ITipoAlimentazioneRepository;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class VeicoliTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ITipoAlimentazioneRepository repTipoA;

	@Autowired
	private ICategoriaRepository repCat;

	@Test
	@Order(1)
	public void createVeicoloTest() throws Exception {
		log.debug("createVeicoloTest");

		TipoAlimentazione alim = new TipoAlimentazione();
		alim.setDescrizione("Benzina");
		alim.setTipoVeicolo("MACCHINA");
		alim = repTipoA.save(alim);

		Categoria cat = new Categoria();
		cat.setDescrizione("Strada");
		cat.setTipoVeicolo("MACCHINA");
		cat = repCat.save(cat);

		MacchinaReq req = new MacchinaReq();
		req.setColore("rosso");
		req.setMarca("Fiat");
		req.setAnnoProduzione(2022);
		req.setModello("500");
		req.setIdAlimentazione(alim.getId());
		req.setIdCategoria(cat.getId());
		req.setCc(1200);
		req.setNumeroPorte(3);
		req.setCodiceTarga("VE123CD");

		mockMvc.perform(post("/rest/macchina/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());
	}
	
	@Test
	@Order(2)
	public void listVeicoliTest() throws Exception{
		log.debug("listVeicoliTest");
		
		MvcResult result = mockMvc.perform(get("/rest/veicoli/list"))
				.andExpect(status().isOk())
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		
		List<VeicoliDTO> lV = objectMapper.readValue(
				json, 
				new TypeReference<List<VeicoliDTO>>() {}
				);
		
		assertFalse(lV.isEmpty());
		
		lV.forEach(v -> log.debug(v.toString())); 
	}
	
	@Test
	@Order(3)
	public void searchVeicoliByMarcaTest() throws Exception {
		log.debug("searchVeicoliByMarcaTest");

		mockMvc.perform(get("/rest/veicoli/search")
				.param("marca", "Fiat"))
				.andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void searchVeicoliByTipoTest() throws Exception {
		log.debug("searchVeicoliByTipoTest");

		mockMvc.perform(get("/rest/veicoli/search")
				.param("tipoVeicolo", "MACCHINA"))
				.andExpect(status().isOk());
	}
}
