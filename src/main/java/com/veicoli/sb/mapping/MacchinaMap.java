package com.veicoli.sb.mapping;

import java.util.List;

import com.veicoli.sb.dto.output.MacchinaDTO;
import com.veicoli.sb.models.Macchina;

public class MacchinaMap {
	public static List<MacchinaDTO> buildMacchinaDTOList(List<Macchina> lM){
		return lM.stream()
				.map(m -> buildMacchinaDTO(m)
						).toList();
	}

	public static MacchinaDTO buildMacchinaDTO(Macchina m) {
		return MacchinaDTO.builder()
				.id(m.getId())
                .colore(m.getColore())
                .marca(m.getMarca())
                .annoProduzione(m.getAnnoProduzione())
                .modello(m.getModello())
                .tipoAlimentazione(m.getTipoAlimentazione() != null ? 
                        m.getTipoAlimentazione().getDescrizione() : null)
                .categoria(m.getCategoria() != null ? 
                        m.getCategoria().getDescrizione() : null)
                .cc(m.getCc())
                .numeroPorte(m.getNumeroPorte())
                .nrRuote(m.getNrRuote())
                .codiceTarga(m.getTarga() != null ? 
                        m.getTarga().getCodice() : null)
				.build();
	}
}
