package com.veicoli.sb.mapping;

import java.util.List;

import com.veicoli.sb.dto.output.MotoDTO;
import com.veicoli.sb.models.Moto;

public class MotoMap {
	public static List<MotoDTO> buildMotoDTOList(List<Moto> lMO){
		return lMO.stream()
				.map(mo -> buildMotoDTO(mo)
						).toList();
	}

	public static MotoDTO buildMotoDTO(Moto mo) {
		return MotoDTO.builder()
				.id(mo.getId())
                .colore(mo.getColore())
                .marca(mo.getMarca())
                .annoProduzione(mo.getAnnoProduzione())
                .modello(mo.getModello())
                .tipoAlimentazione(mo.getTipoAlimentazione() != null ? 
                        mo.getTipoAlimentazione().getDescrizione() : null)
                .categoria(mo.getCategoria() != null ? 
                        mo.getCategoria().getDescrizione() : null)
                .cc(mo.getCc())
                .nrRuote(mo.getNrRuote())
                .codiceTarga(mo.getTarga() != null ? 
                        mo.getTarga().getCodice() : null)
				.build();
	}
}
