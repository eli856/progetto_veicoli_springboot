package com.veicoli.sb.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.veicoli.sb.dto.output.VeicoliDTO;
import com.veicoli.sb.mapping.VeicoliMap;
import com.veicoli.sb.models.Veicoli;
import com.veicoli.sb.repositories.IVeicoliRepository;
import com.veicoli.sb.services.interfaces.IVeicoliServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class VeicoliImpl implements IVeicoliServices{

	private final IVeicoliRepository  repV;
	
	@Override
	public List<VeicoliDTO> list() throws Exception {
		log.debug("list Veicoli");
		List<Veicoli> lV = repV.findAll();
		return VeicoliMap.buildVeicoliDTOList(lV);
	}

	@Override
	public List<VeicoliDTO> search(
			String tipoVeicolo, 
			String marca, 
			String modello, 
			String colore,
			Integer annoProduzione, 
			String tipoAlimentazione, 
			String categoria, 
			String targa
	) throws Exception {
		List<Veicoli> lV = repV.search(
			tipoVeicolo, 
			marca, 
			modello, 
			colore, 
			annoProduzione, 
			tipoAlimentazione, 
			categoria, 
			targa
		);
		return VeicoliMap.buildVeicoliDTOList(lV);
	}

	
}
