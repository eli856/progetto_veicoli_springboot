package com.veicoli.sb.moto;

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

import com.veicoli.sb.dto.input.MotoReq;
import com.veicoli.sb.dto.output.MotoDTO;
import com.veicoli.sb.dto.output.ResponseDTO;
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
public class MotoTest {

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
	public void createMotoTest() throws Exception {
		log.debug("createMotoTest");

		TipoAlimentazione alim = new TipoAlimentazione();
		alim.setDescrizione("Benzina");
		alim.setTipoVeicolo("MOTO");
		alim = repTipoA.save(alim);

		Categoria cat = new Categoria();
		cat.setDescrizione("Sportiva");
		cat.setTipoVeicolo("MOTO");
		cat = repCat.save(cat);

		MotoReq req = new MotoReq();
		req.setColore("nero");
		req.setMarca("Ducati");
		req.setAnnoProduzione(2023);
		req.setModello("Monster");
		req.setIdAlimentazione(alim.getId());
		req.setIdCategoria(cat.getId());
		req.setCc(900);
		req.setCodiceTarga("MT456EF");

		mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void createMotoErrorTest() throws Exception{
		log.debug("createMotoErrorTest");

		MotoReq req = new MotoReq();
		req.setColore("nero");

		mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.msg").exists());
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
	public void updateMotoTest() throws Exception{
		log.debug("updateMotoTest");

		MotoReq req = new MotoReq();
		req.setId(4);
		req.setColore("Verde");

		mockMvc.perform(patch("/rest/moto/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void deleteMotoTest() throws Exception{
		log.debug("deleteMotoTest");

		TipoAlimentazione alim = new TipoAlimentazione();
		alim.setDescrizione("Benzina");
		alim.setTipoVeicolo("MOTO");
		alim = repTipoA.save(alim);

		Categoria cat = new Categoria();
		cat.setDescrizione("Sportiva");
		cat.setTipoVeicolo("MOTO");
		cat = repCat.save(cat);

		MotoReq req = new MotoReq();
		req.setColore("nero");
		req.setMarca("Ducati");
		req.setAnnoProduzione(2023);
		req.setModello("Monster");
		req.setIdAlimentazione(alim.getId());
		req.setIdCategoria(cat.getId());
		req.setCc(900);
		req.setCodiceTarga("MT456BB");

		MvcResult result = mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isOk())
		.andReturn();

		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("Moto creata :{}", dto.getMsg());

		mockMvc.perform(delete("/rest/moto/delete" + "/" + "4"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.msg").exists());

		log.debug("Moto deleted....");
	}

	@Test
	@Order(5)
	public void listMotoTest() throws Exception{
		log.debug("listMotoTest");

		MvcResult result = mockMvc.perform(get("/rest/moto/list"))
				.andExpect(status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();

		List<MotoDTO> lM = objectMapper.readValue(
				json,
				new TypeReference<List<MotoDTO>>() {}
				);
		assertFalse(lM.isEmpty());

		lM.forEach(m -> log.debug(m.toString()));
	}

	@Test
	@Order(6)
	public void getByIdMotoTest() throws Exception{
		log.debug("getByIdMotoTest");

		mockMvc.perform(get("/rest/moto/getById")
				.param("id", "5"))
				.andExpect(status().isOk());
	}

}
