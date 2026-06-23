package com.veicoli.sb.macchina;

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

import com.veicoli.sb.dto.input.MacchinaReq;
import com.veicoli.sb.dto.output.MacchinaDTO;
import com.veicoli.sb.dto.output.ResponseDTO;
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
public class MacchinaTest {

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
	public void createMacchinaTest() throws Exception{
		log.debug("createMacchinaTest");

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
		req.setCodiceTarga("MA123SD");


		mockMvc.perform(post("/rest/macchina/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());

	}

	@Test
	@Order(2)
	public void createMacchinaErrorTest() throws Exception{
		log.debug("createMacchinaErrorTest");

		MacchinaReq req = new MacchinaReq();
		req.setColore("rosso");

		MvcResult result = mockMvc.perform(post("/rest/macchina/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isBadRequest())
		.andReturn();

		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("Macchina create :{}", dto.getMsg());
	}

	@Test
	@Order(3)
	public void updateMacchinaTest() throws Exception{
		log.debug("updateMacchinaTest");

		MacchinaReq req = new MacchinaReq();
		req.setId(1);
		req.setColore("Verde");

		mockMvc.perform(patch("/rest/macchina/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				).andExpect(status().isOk());
	}


	@Test
	@Order(4)
	public void deleteMacchinaTest() throws Exception{
		log.debug("deleteMacchinaTest");

		TipoAlimentazione alim = new TipoAlimentazione();
		alim.setDescrizione("Benzina");
		alim.setTipoVeicolo("MACCHINA");
		alim = repTipoA.save(alim);

		Categoria cat = new Categoria();
		cat.setDescrizione("Strada");
		cat.setTipoVeicolo("MACCHINA");
		cat = repCat.save(cat);

		MacchinaReq req = new MacchinaReq();
		req.setId(2);
		req.setColore("rosso");
		req.setMarca("Fiat");
		req.setAnnoProduzione(2022);
		req.setModello("500");
		req.setIdAlimentazione(alim.getId());
		req.setIdCategoria(cat.getId());
		req.setCc(1200);
		req.setNumeroPorte(3);
		req.setCodiceTarga("MA123VV");

		MvcResult result = mockMvc.perform(post("/rest/macchina/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isOk())
		.andReturn();

		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("Macchina creata :{}", dto.getMsg());

		mockMvc.perform(delete("/rest/macchina/delete" + "/" + "2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.msg").exists());

		log.debug("Macchina deleted....");
	}

	@Test
	@Order(5)
	public void listMacchinaTest() throws Exception{
		log.debug("listMacchinaTest");

		MvcResult result = mockMvc.perform(get("/rest/macchina/list"))
				.andExpect(status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();

		List<MacchinaDTO> lM = objectMapper.readValue(
				json,
				new TypeReference<List<MacchinaDTO>>() {}
				);
		assertFalse(lM.isEmpty());

		lM.forEach(m -> log.debug(m.toString()));
	}

	@Test
	@Order(6)
	public void getByIdMacchinaTest() throws Exception{
		log.debug("getByIdMacchinaTest");

		mockMvc.perform(get("/rest/macchina/getById")
				.param("id", "1"))
				.andExpect(status().isOk());
	}
}
