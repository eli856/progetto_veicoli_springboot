package com.veicoli.sb.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veicoli.sb.dto.input.BiciReq;
import com.veicoli.sb.dto.output.BiciDTO;
import com.veicoli.sb.exceptions.VeicoliExceptions;
import com.veicoli.sb.mapping.BiciMap;
import com.veicoli.sb.models.Bici;
import com.veicoli.sb.models.Categoria;
import com.veicoli.sb.models.TipoAlimentazione;
import com.veicoli.sb.models.TipoFreno;
import com.veicoli.sb.models.TipoSospensione;
import com.veicoli.sb.repositories.IBiciRepository;
import com.veicoli.sb.repositories.ICategoriaRepository;
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
	
	private static final String TIPO = "BICI";
	
	@Transactional
	@Override
	public void create(BiciReq req) throws Exception {
		log.debug("create Bici {}", req);
		
		TipoAlimentazione alimentazione = repTipoA
		        .findByIdAndTipoVeicolo(req.getIdAlimentazione(), TIPO)
		        .orElseThrow(() -> new VeicoliExceptions("alim_invalid_bici"));
	    
	    Categoria cat = repC
	    		.findByIdAndTipoVeicolo(req.getIdCategoria(), TIPO)
	    		.orElseThrow(() -> new VeicoliExceptions("cat_invalid_bici"));
		
		    TipoFreno tipoFreno = repTipoF
			        .findById(req.getTipoFreno())
			        .orElseThrow(() -> new VeicoliExceptions("freno_ntfnd"));
		    
		    TipoSospensione sospensione = repTipoS
			        .findById(req.getTipoSospensione())
			        .orElseThrow(() -> new VeicoliExceptions("sosp_ntfnd"));
		    
		    Bici bici = new Bici();
		    bici.setColore(req.getColore());
		    bici.setMarca(req.getMarca());
		    bici.setAnnoProduzione(req.getAnnoProduzione());
		    bici.setModello(req.getModello());
		    bici.setTipoAlimentazione(alimentazione);
		    bici.setCategoria(cat);
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
			throw new VeicoliExceptions("id_required");
		
		Bici bi = repB.findById(req.getId())
				.orElseThrow(() -> new VeicoliExceptions("veicolo_ntfnd"));
		// veicoli fields
		if (req.getColore() != null) bi.setColore(req.getColore());
	    if (req.getMarca() != null) bi.setMarca(req.getMarca());
	    if (req.getAnnoProduzione() != null) bi.setAnnoProduzione(req.getAnnoProduzione());
	    if (req.getModello() != null) bi.setModello(req.getModello());
	    // bici-specific fields
	    if (req.getNumeroMarce() != null) bi.setNumeroMarce(req.getNumeroMarce());
	    if (req.getPieghevole() != null) bi.setPieghevole(req.getPieghevole());
	    
	    if (req.getIdAlimentazione() != null) {
	        TipoAlimentazione alim = repTipoA
	                .findByIdAndTipoVeicolo(req.getIdAlimentazione(), TIPO)
	                .orElseThrow(() -> new VeicoliExceptions("alim_invalid_bici"));
	        bi.setTipoAlimentazione(alim);
	    }
	    if (req.getIdCategoria() != null) {
	        Categoria cat = repC
	                .findByIdAndTipoVeicolo(req.getIdCategoria(), TIPO)
	                .orElseThrow(() -> new VeicoliExceptions("cat_invalid_bici"));
	        bi.setCategoria(cat);
	    }
	    
	    if (req.getTipoFreno() != null) {
	        TipoFreno freno = repTipoF.findById(req.getTipoFreno())
	                .orElseThrow(() -> new VeicoliExceptions("freno_ntfnd"));
	        bi.setTipoFreno(freno);
	    }
	    if (req.getTipoSospensione() != null) {
	    	TipoSospensione sosp = repTipoS.findById(req.getTipoSospensione())
	                .orElseThrow(() -> new VeicoliExceptions("sosp_ntfnd"));
	        bi.setTipoSospensione(sosp);
	    }
	    
	    repB.save(bi);
	    
	}

	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete Bici {}", id);
		
		Bici bici = repB.findById(id)
				.orElseThrow(() -> new VeicoliExceptions("bici_ntfnd"));
		
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
	            .orElseThrow(() -> new VeicoliExceptions("bici_ntfnd"));
	    return BiciMap.buildBiciDTO(bi);
	}

}
