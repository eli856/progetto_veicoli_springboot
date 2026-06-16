package com.veicoli.sb.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veicoli.sb.dto.input.MotoReq;
import com.veicoli.sb.dto.output.MotoDTO;
import com.veicoli.sb.exceptions.VeicoliExceptions;
import com.veicoli.sb.mapping.MotoMap;
import com.veicoli.sb.models.Categoria;
import com.veicoli.sb.models.Moto;
import com.veicoli.sb.models.Targa;
import com.veicoli.sb.models.TipoAlimentazione;
import com.veicoli.sb.repositories.ICategoriaRepository;
import com.veicoli.sb.repositories.IMotoRepository;
import com.veicoli.sb.repositories.ITipoAlimentazioneRepository;
import com.veicoli.sb.services.interfaces.IMotoServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MotoImpl implements IMotoServices{
	
	private final IMotoRepository repMo;
	private final ITipoAlimentazioneRepository repTipoA;
	private final ICategoriaRepository repC;

	@Transactional
	@Override
	public void create(MotoReq req) throws Exception {
		log.debug("create Moto {}", req);
		
		TipoAlimentazione alimentazione = repTipoA
		        .findById(req.getIdAlimentazione())
		        .orElseThrow(() -> new VeicoliExceptions("Alimentazione non trovata"));

		    Categoria categoria = repC
		        .findById(req.getIdCategoria())
		        .orElseThrow(() -> new VeicoliExceptions("Categoria non trovata"));
		    
		    Moto moto = new Moto();
		    moto.setColore(req.getColore());
		    moto.setMarca(req.getMarca());
		    moto.setAnnoProduzione(req.getAnnoProduzione());
		    moto.setModello(req.getModello());
		    moto.setTipoAlimentazione(alimentazione);
		    moto.setCategoria(categoria);
		    moto.setCc(req.getCc());
		    moto.setNrRuote(2);
		    
		    Targa targa = new Targa();
		    targa.setCodice(req.getCodiceTarga());
		    targa.setMoto(moto);
		    
		    moto.setTarga(targa);
		    
		    repMo.save(moto);
	}

	@Transactional
	@Override
	public void update(MotoReq req) throws Exception {
		log.debug("update Moto {}", req);
		
		if(req.getId() == null)
			throw new VeicoliExceptions("id non fornito");
		
		Moto Mo = repMo.findById(req.getId())
				.orElseThrow(()-> new VeicoliExceptions("veicolo non trovato"));
		
		// veicoli fields
	    if (req.getColore() != null) Mo.setColore(req.getColore());
	    if (req.getMarca() != null) Mo.setMarca(req.getMarca());
	    if (req.getAnnoProduzione() != null) Mo.setAnnoProduzione(req.getAnnoProduzione());
	    if (req.getModello() != null) Mo.setModello(req.getModello());
	    
	    // FK fields
	    if (req.getIdAlimentazione() != null) {
	        TipoAlimentazione alim = repTipoA.findById(req.getIdAlimentazione())
	                .orElseThrow(() -> new VeicoliExceptions("alimentazione non trovata"));
	        Mo.setTipoAlimentazione(alim);
	    }
	    if (req.getIdCategoria() != null) {
	        Categoria cat = repC.findById(req.getIdCategoria())
	                .orElseThrow(() -> new VeicoliExceptions("categoria non trovata"));
	        Mo.setCategoria(cat);
	    }
	    
	    //moto fields
	    if (req.getCc() != null) Mo.setCc(req.getCc());
	    if (req.getNrRuote() != null) Mo.setNrRuote(req.getNrRuote());

	    // targa
	    if (req.getCodiceTarga() != null) {
	        if (Mo.getTarga() != null)
	            Mo.getTarga().setCodice(req.getCodiceTarga());
	        else {
	            Targa targa = new Targa();
	            targa.setCodice(req.getCodiceTarga());
	            targa.setMoto(Mo);
	            Mo.setTarga(targa);
	        }
	    }

	    repMo.save(Mo);
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete Moto {}", id);
		
		Moto mot = repMo.findById(id)
				.orElseThrow(() -> new VeicoliExceptions("moto non trovato"));
		
		repMo.delete(mot);
		
		
	}

	@Override
	public List<MotoDTO> list() throws Exception {
		log.debug("list Moto");
		List<Moto> lMO = repMo.findAll();
		return MotoMap.buildMotoDTOList(lMO);
	}

	@Override
	public MotoDTO getById(Integer id) throws Exception {
		Moto mo = repMo.findById(id)
	            .orElseThrow(() -> new VeicoliExceptions("moto non trovato"));
	    return MotoMap.buildMotoDTO(mo);
	}

}
