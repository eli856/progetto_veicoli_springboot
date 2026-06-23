package com.veicoli.sb.bici;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.veicoli.sb.dto.input.BiciReq;
import com.veicoli.sb.dto.output.BiciDTO;
import com.veicoli.sb.dto.output.ResponseDTO;
import com.veicoli.sb.dto.output.VeicoliDTO;
import com.veicoli.sb.models.Categoria;
import com.veicoli.sb.models.TipoAlimentazione;
import com.veicoli.sb.models.TipoFreno;
import com.veicoli.sb.models.TipoSospensione;
import com.veicoli.sb.repositories.ICategoriaRepository;
import com.veicoli.sb.repositories.ITipoAlimentazioneRepository;
import com.veicoli.sb.repositories.ITipoFrenoRepository;
import com.veicoli.sb.repositories.ITipoSospensioneRepository;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class BiciTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ITipoAlimentazioneRepository repTipoA;

	@Autowired
	private ICategoriaRepository repCat;

	@Autowired
	private ITipoFrenoRepository repFreno;

	@Autowired
	private ITipoSospensioneRepository repSosp;

	@Test
	@Order(1)
	public void createBiciTest() throws Exception {
		log.debug("createBiciTest");

		TipoAlimentazione alim = new TipoAlimentazione();
		alim.setDescrizione("Manual");
		alim.setTipoVeicolo("BICI");
		alim = repTipoA.save(alim);

		Categoria cat = new Categoria();
		cat.setDescrizione("Montagna");
		cat.setTipoVeicolo("BICI");
		cat = repCat.save(cat);

		TipoFreno freno = new TipoFreno();
		freno.setDescrizione("Disco");
		freno = repFreno.save(freno);

		TipoSospensione sosp = new TipoSospensione();
		sosp.setDescrizione("Mono");
		sosp = repSosp.save(sosp);

		BiciReq req = new BiciReq();
		req.setColore("blu");
		req.setMarca("Bianchi");
		req.setAnnoProduzione(2021);
		req.setModello("MTB-1");
		req.setIdAlimentazione(alim.getId());
		req.setIdCategoria(cat.getId());
		req.setNumeroMarce(21);
		req.setPieghevole(false);
		req.setTipoFreno(freno.getId());
		req.setTipoSospensione(sosp.getId());

		mockMvc.perform(post("/rest/bici/create")
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
	@Order(2)
	public void createBiciErrorTest() throws Exception {
		log.debug("createBiciErrorTest");

		BiciReq req = new BiciReq();
		req.setColore("blu");

		mockMvc.perform(post("/rest/bici/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.msg").exists());
	}

	@Test
	@Order(3)
	public void updateBiciTest() throws Exception {
		log.debug("updateBiciTest");

		BiciReq req = new BiciReq();
		req.setId(6);
		req.setColore("giallo");

		mockMvc.perform(patch("/rest/bici/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void deleteBiciTest() throws Exception{
		log.debug("deleteBiciTest");

		TipoAlimentazione alim = new TipoAlimentazione();
		alim.setDescrizione("Manual");
		alim.setTipoVeicolo("BICI");
		alim = repTipoA.save(alim);

		Categoria cat = new Categoria();
		cat.setDescrizione("Montagna");
		cat.setTipoVeicolo("BICI");
		cat = repCat.save(cat);

		TipoFreno freno = new TipoFreno();
		freno.setDescrizione("Disco");
		freno = repFreno.save(freno);

		TipoSospensione sosp = new TipoSospensione();
		sosp.setDescrizione("Mono");
		sosp = repSosp.save(sosp);

		BiciReq req = new BiciReq();
		req.setColore("blu");
		req.setMarca("Bianchi");
		req.setAnnoProduzione(2021);
		req.setModello("MTB-1");
		req.setIdAlimentazione(alim.getId());
		req.setIdCategoria(cat.getId());
		req.setNumeroMarce(21);
		req.setPieghevole(false);
		req.setTipoFreno(freno.getId());
		req.setTipoSospensione(sosp.getId());

		MvcResult result = mockMvc.perform(post("/rest/bici/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isOk())
		.andReturn();

		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("Bici creata :{}", dto.getMsg());

		mockMvc.perform(delete("/rest/bici/delete" + "/" + "6"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.msg").exists());

		log.debug("Bici deleted....");
	}

	@Test
	@Order(5)
	public void listBiciTest() throws Exception {
		log.debug("listBiciTest");

		MvcResult result = mockMvc.perform(get("/rest/bici/list"))
				.andExpect(status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();

		List<BiciDTO> lB = objectMapper.readValue(
				json,
				new TypeReference<List<BiciDTO>>() {}
				);

		assertFalse(lB.isEmpty());

		lB.forEach(b -> log.debug(b.toString()));
	}

	@Test
	@Order(6)
	public void getByIdBiciTest() throws Exception {
		log.debug("getByIdBiciTest");

		mockMvc.perform(get("/rest/bici/getById")
				.param("id", "7"))
				.andExpect(status().isOk());
	}
}
