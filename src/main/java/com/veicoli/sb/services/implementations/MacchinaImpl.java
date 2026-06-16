package com.veicoli.sb.services.implementations;

import java.util.List;


import org.springframework.stereotype.Service;
import com.veicoli.sb.dto.input.MacchinaReq;
import com.veicoli.sb.dto.output.MacchinaDTO;
import com.veicoli.sb.exceptions.VeicoliExceptions;
import com.veicoli.sb.mapping.MacchinaMap;
import com.veicoli.sb.models.Categoria;
import com.veicoli.sb.models.Macchina;
import com.veicoli.sb.models.Targa;
import com.veicoli.sb.models.TipoAlimentazione;
import com.veicoli.sb.repositories.ICategoriaRepository;
import com.veicoli.sb.repositories.IMacchinaRepository;
import com.veicoli.sb.repositories.ITipoAlimentazioneRepository;
import com.veicoli.sb.services.interfaces.IMacchinaServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MacchinaImpl implements IMacchinaServices{
	
	private final IMacchinaRepository repM;
	private final ITipoAlimentazioneRepository repTipoA;
	private final ICategoriaRepository repC;
	
	@Transactional
	@Override
	public void create(MacchinaReq req) throws Exception {
		log.debug("create Macchina {}", req);
		
		TipoAlimentazione alimentazione = repTipoA
		        .findById(req.getIdAlimentazione())
		        .orElseThrow(() -> new VeicoliExceptions("Alimentazione non trovata"));

		    Categoria categoria = repC
		        .findById(req.getIdCategoria())
		        .orElseThrow(() -> new VeicoliExceptions("Categoria non trovata"));

		    // step 2 - build entity
		    Macchina macchina = new Macchina();
		    macchina.setColore(req.getColore());
		    macchina.setMarca(req.getMarca());
		    macchina.setAnnoProduzione(req.getAnnoProduzione());
		    macchina.setModello(req.getModello());
		    macchina.setTipoAlimentazione(alimentazione);
		    macchina.setCategoria(categoria);
		    macchina.setCc(req.getCc());
		    macchina.setNumeroPorte(req.getNumeroPorte());
		    macchina.setNrRuote(4);

		    // step 3 - handle targa
		    Targa targa = new Targa();
		    targa.setCodice(req.getCodiceTarga());
		    targa.setMacchina(macchina);

		    macchina.setTarga(targa);

		    // step 4 - save (JPA saves into veicoli + macchina + targa)
		    repM.save(macchina);
	}

	@Transactional
	@Override
	public void update(MacchinaReq req) throws Exception {
		log.debug("update Macchina {}", req);
		
		if(req.getId() == null)
			throw new VeicoliExceptions("id non fornito");
		
		Macchina m = repM.findById(req.getId())
				.orElseThrow(()-> new VeicoliExceptions("veicolo non trovato"));
		
		 // veicoli fields
	    if (req.getColore() != null) m.setColore(req.getColore());
	    if (req.getMarca() != null) m.setMarca(req.getMarca());
	    if (req.getAnnoProduzione() != null) m.setAnnoProduzione(req.getAnnoProduzione());
	    if (req.getModello() != null) m.setModello(req.getModello());

	    // FK fields
	    if (req.getIdAlimentazione() != null) {
	        TipoAlimentazione alim = repTipoA.findById(req.getIdAlimentazione())
	                .orElseThrow(() -> new VeicoliExceptions("alimentazione non trovata"));
	        m.setTipoAlimentazione(alim);
	    }

	    if (req.getIdCategoria() != null) {
	        Categoria cat = repC.findById(req.getIdCategoria())
	                .orElseThrow(() -> new VeicoliExceptions("categoria non trovata"));
	        m.setCategoria(cat);
	    }

	    // macchina fields
	    if (req.getCc() != null) m.setCc(req.getCc());
	    if (req.getNumeroPorte() != null) m.setNumeroPorte(req.getNumeroPorte());
	    if (req.getNrRuote() != null) m.setNrRuote(req.getNrRuote());

	    // targa
	    if (req.getCodiceTarga() != null) {
	        if (m.getTarga() != null)
	            m.getTarga().setCodice(req.getCodiceTarga());
	        else {
	            Targa targa = new Targa();
	            targa.setCodice(req.getCodiceTarga());
	            targa.setMacchina(m);
	            m.setTarga(targa);
	        }
	    }

	    repM.save(m);
	}

	@Transactional
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete Macchina {}", id);
		
		Macchina macc = repM.findById(id)
				.orElseThrow(() -> new VeicoliExceptions("macchina non trovato"));
		
		repM.delete(macc);
		
	}

	@Override
	public List<MacchinaDTO> list() throws Exception {
		log.debug("list Macchina");
		List<Macchina> lM = repM.findAll();
		return MacchinaMap.buildMacchinaDTOList(lM);
	}

	@Override
	public MacchinaDTO getById(Integer id) throws Exception {
	    Macchina m = repM.findById(id)
	            .orElseThrow(() -> new VeicoliExceptions("macchina non trovata"));
	    return MacchinaMap.buildMacchinaDTO(m);
	}

}
