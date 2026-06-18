package com.veicoli.sb.mapping;

import java.util.List;

import com.veicoli.sb.dto.output.MotoDTO;
import com.veicoli.sb.dto.output.VeicoliDTO;
import com.veicoli.sb.models.Bici;
import com.veicoli.sb.models.Macchina;
import com.veicoli.sb.models.Moto;
import com.veicoli.sb.models.Veicoli;

public class VeicoliMap {
	public static List<VeicoliDTO> buildVeicoliDTOList(List<Veicoli> lV){
		return lV.stream()
				.map(v -> buildVeicoliDTO(v)
						).toList();
	}

	public static VeicoliDTO buildVeicoliDTO(Veicoli v) {
		String tipoVeicolo = null;
	    String codiceTarga = null;
	    String tipoAlim = v.getTipoAlimentazione() != null ? v.getTipoAlimentazione().getDescrizione() : null;
	    String categoria = v.getCategoria() != null ? v.getCategoria().getDescrizione() : null;

	    if (v instanceof Macchina mc) {
	        tipoVeicolo = "MACCHINA";
	        if (mc.getTarga() != null) codiceTarga = mc.getTarga().getCodice();
	    } else if (v instanceof Moto mo) {
	        tipoVeicolo = "MOTO";
	        if (mo.getTarga() != null) codiceTarga = mo.getTarga().getCodice();
	    } else if (v instanceof Bici) {
	        tipoVeicolo = "BICI";
	    } 
		
		return VeicoliDTO.builder()
				.id(v.getId())
	            .tipoVeicolo(tipoVeicolo)
	            .colore(v.getColore())
	            .marca(v.getMarca())
	            .modello(v.getModello())
	            .annoProduzione(v.getAnnoProduzione())
	            .tipoAlimentazione(tipoAlim)
	            .categoria(categoria)
	            .codiceTarga(codiceTarga)
	            .build();
	}
}
