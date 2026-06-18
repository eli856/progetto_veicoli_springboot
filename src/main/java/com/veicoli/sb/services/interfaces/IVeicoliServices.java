package com.veicoli.sb.services.interfaces;

import java.util.List;

import com.veicoli.sb.dto.output.VeicoliDTO;

public interface IVeicoliServices {
	
	List<VeicoliDTO> list() throws Exception;
	 
	List<VeicoliDTO> search(
			String tipoVeicolo,
            String marca,
            String modello,
            String colore,
            Integer annoProduzione,
            String tipoAlimentazione,
            String categoria,
            String targa
    ) throws Exception;
}
