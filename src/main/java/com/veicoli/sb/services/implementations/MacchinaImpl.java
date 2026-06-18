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
import com.veicoli.sb.repositories.ITargaRepository;
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
	private final ITargaRepository repTarga;
	
	private static final String TIPO = "MACCHINA";
	
	@Transactional
	@Override
	public void create(MacchinaReq req) throws Exception {
		log.debug("create Macchina {}", req);
		
		    if (repTarga.existsByCodice(req.getCodiceTarga())) {
		        throw new VeicoliExceptions("targa_duplicate");
		    }
		    
		    TipoAlimentazione alimentazione = repTipoA
			        .findByIdAndTipoVeicolo(req.getIdAlimentazione(), TIPO)
			        .orElseThrow(() -> new VeicoliExceptions("alim_invalid_macch"));
		    
		    Categoria cat = repC
		    		.findByIdAndTipoVeicolo(req.getIdCategoria(), TIPO)
		    		.orElseThrow(() -> new VeicoliExceptions("cat_invalid_macch"));

		    // step 2 - build entity
		    Macchina macchina = new Macchina();
		    macchina.setColore(req.getColore());
		    macchina.setMarca(req.getMarca());
		    macchina.setAnnoProduzione(req.getAnnoProduzione());
		    macchina.setModello(req.getModello());
		    macchina.setTipoAlimentazione(alimentazione);
		    macchina.setCategoria(cat);
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
			throw new VeicoliExceptions("id_required");
		
		Macchina m = repM.findById(req.getId())
				.orElseThrow(()-> new VeicoliExceptions("veicolo_ntfnd"));
		
		 // veicoli fields
	    if (req.getColore() != null) m.setColore(req.getColore());
	    if (req.getMarca() != null) m.setMarca(req.getMarca());
	    if (req.getAnnoProduzione() != null) m.setAnnoProduzione(req.getAnnoProduzione());
	    if (req.getModello() != null) m.setModello(req.getModello());

	    // FK fields
	    if (req.getIdAlimentazione() != null) {
	        TipoAlimentazione alim = repTipoA
	                .findByIdAndTipoVeicolo(req.getIdAlimentazione(), TIPO)
	                .orElseThrow(() -> new VeicoliExceptions("alim_invalid_macch"));
	        m.setTipoAlimentazione(alim);
	    }
	    if (req.getIdCategoria() != null) {
	        Categoria cat = repC
	                .findByIdAndTipoVeicolo(req.getIdCategoria(), TIPO)
	                .orElseThrow(() -> new VeicoliExceptions("cat_invalid_macch"));
	        m.setCategoria(cat);
	    }

	    // macchina fields
	    if (req.getCc() != null) m.setCc(req.getCc());
	    if (req.getNumeroPorte() != null) m.setNumeroPorte(req.getNumeroPorte());
//	    if (req.getNrRuote() != null) m.setNrRuote(req.getNrRuote());

	    // targa
	    if (req.getCodiceTarga() != null) {
	        Integer currentTargaId = m.getTarga() != null ? m.getTarga().getId() : -1;

	        if (repTarga.existsByCodiceAndIdNot(req.getCodiceTarga(), currentTargaId)) {
	            throw new VeicoliExceptions("targa_duplicate");
	        }

	        if (m.getTarga() != null) {
	            m.getTarga().setCodice(req.getCodiceTarga());
	        } else {
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
				.orElseThrow(() -> new VeicoliExceptions("macchina_ntfnd"));
		
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
	            .orElseThrow(() -> new VeicoliExceptions("macchina_ntfnd"));
	    return MacchinaMap.buildMacchinaDTO(m);
	}

}
