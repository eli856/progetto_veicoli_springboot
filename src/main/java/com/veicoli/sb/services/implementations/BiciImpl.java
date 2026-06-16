package com.veicoli.sb.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veicoli.sb.dto.input.BiciReq;
import com.veicoli.sb.dto.output.BiciDTO;
import com.veicoli.sb.exceptions.VeicoliExceptions;
import com.veicoli.sb.mapping.BiciMap;
import com.veicoli.sb.mapping.MotoMap;
import com.veicoli.sb.models.Bici;
import com.veicoli.sb.models.Categoria;
import com.veicoli.sb.models.Moto;
import com.veicoli.sb.models.Targa;
import com.veicoli.sb.models.TipoAlimentazione;
import com.veicoli.sb.models.TipoFreno;
import com.veicoli.sb.models.TipoSospensione;
import com.veicoli.sb.repositories.IBiciRepository;
import com.veicoli.sb.repositories.ICategoriaRepository;
import com.veicoli.sb.repositories.IMotoRepository;
import com.veicoli.sb.repositories.ITipoAlimentazioneRepository;
import com.veicoli.sb.repositories.ITipoFrenoRepository;
import com.veicoli.sb.repositories.ITipoSospensioneRepository;
import com.veicoli.sb.services.interfaces.IBiciServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BiciImpl implements IBiciServices{
	
	private final IBiciRepository repB;
	private final ITipoAlimentazioneRepository repTipoA;
	private final ICategoriaRepository repC;
	private final ITipoFrenoRepository repTipoF;
	private final ITipoSospensioneRepository repTipoS;
	
	@Transactional
	@Override
	public void create(BiciReq req) throws Exception {
		log.debug("create Bici {}", req);
		
		TipoAlimentazione alimentazione = repTipoA
		        .findById(req.getIdAlimentazione())
		        .orElseThrow(() -> new VeicoliExceptions("Alimentazione non trovata"));

		    Categoria categoria = repC
		        .findById(req.getIdCategoria())
		        .orElseThrow(() -> new VeicoliExceptions("Categoria non trovata"));
		    
		    TipoFreno tipoFreno = repTipoF
			        .findById(req.getTipoFreno())
			        .orElseThrow(() -> new VeicoliExceptions("Tipo freno non trovata"));
		    
		    TipoSospensione sospensione = repTipoS
			        .findById(req.getTipoSospensione())
			        .orElseThrow(() -> new VeicoliExceptions("Tipo Sospensione non trovata"));
		    
		    Bici bici = new Bici();
		    bici.setColore(req.getColore());
		    bici.setMarca(req.getMarca());
		    bici.setAnnoProduzione(req.getAnnoProduzione());
		    bici.setModello(req.getModello());
		    bici.setTipoAlimentazione(alimentazione);
		    bici.setCategoria(categoria);
		    bici.setNrRuote(2);
		    
		    bici.setNumeroMarce(req.getNumeroMarce());
		    bici.setPieghevole(req.getPieghevole());
		    bici.setTipoFreno(tipoFreno);
		    bici.setTipoSospensione(sospensione);
		    
		    repB.save(bici);
		
	}

	@Transactional
	@Override
	public void update(BiciReq req) throws Exception {
		log.debug("update Bici {}", req);
		
		if(req.getId() == null)
			throw new VeicoliExceptions("id non fornito");
		
		Bici bi = repB.findById(req.getId())
				.orElseThrow(() -> new VeicoliExceptions("veicolo non trovato"));
		// veicoli fields
		if (req.getColore() != null) bi.setColore(req.getColore());
	    if (req.getMarca() != null) bi.setMarca(req.getMarca());
	    if (req.getAnnoProduzione() != null) bi.setAnnoProduzione(req.getAnnoProduzione());
	    if (req.getModello() != null) bi.setModello(req.getModello());
	    // bici-specific fields
	    if (req.getNumeroMarce() != null) bi.setNumeroMarce(req.getNumeroMarce());
	    if (req.getPieghevole() != null) bi.setPieghevole(req.getPieghevole());
	    
	    if (req.getIdAlimentazione() != null) {
	        TipoAlimentazione alim = repTipoA.findById(req.getIdAlimentazione())
	                .orElseThrow(() -> new VeicoliExceptions("alimentazione non trovata"));
	        bi.setTipoAlimentazione(alim);
	    }
	    if (req.getIdCategoria() != null) {
	        Categoria cat = repC.findById(req.getIdCategoria())
	                .orElseThrow(() -> new VeicoliExceptions("categoria non trovata"));
	        bi.setCategoria(cat);
	    }
	    if (req.getTipoFreno() != null) {
	        TipoFreno freno = repTipoF.findById(req.getTipoFreno())
	                .orElseThrow(() -> new VeicoliExceptions("Tipo Freno non trovata"));
	        bi.setTipoFreno(freno);
	    }
	    if (req.getTipoSospensione() != null) {
	    	TipoSospensione sosp = repTipoS.findById(req.getTipoSospensione())
	                .orElseThrow(() -> new VeicoliExceptions("Tipo Sospensione non trovata"));
	        bi.setTipoSospensione(sosp);
	    }
	    
	    repB.save(bi);
	    
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete Bici {}", id);
		
		Bici bici = repB.findById(id)
				.orElseThrow(() -> new VeicoliExceptions("bici non trovato"));
		
		repB.delete(bici);
	}

	@Override
	public List<BiciDTO> list() throws Exception {
		log.debug("list Moto");
		List<Bici> lB = repB.findAll();
		return BiciMap.buildBiciDTOList(lB);
	}

	@Override
	public BiciDTO getById(Integer id) throws Exception {
		Bici bi = repB.findById(id)
	            .orElseThrow(() -> new VeicoliExceptions("bici non trovato"));
	    return BiciMap.buildBiciDTO(bi);
	}

}
