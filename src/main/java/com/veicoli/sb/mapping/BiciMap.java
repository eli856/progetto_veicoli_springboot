package com.veicoli.sb.mapping;

import java.util.List;

import com.veicoli.sb.dto.output.BiciDTO;
import com.veicoli.sb.models.Bici;

public class BiciMap {
	public static List<BiciDTO> buildBiciDTOList(List<Bici> lB){
		return lB.stream()
				.map(bi -> buildBiciDTO(bi)
						).toList();
	}
	
	public static BiciDTO buildBiciDTO(Bici bi) {
		return BiciDTO.builder()
				.id(bi.getId())
                .colore(bi.getColore())
                .marca(bi.getMarca())
                .annoProduzione(bi.getAnnoProduzione())
                .modello(bi.getModello())
                .tipoAlimentazione(bi.getTipoAlimentazione() != null ? 
                        bi.getTipoAlimentazione().getDescrizione() : null)
                .categoria(bi.getCategoria() != null ? 
                        bi.getCategoria().getDescrizione() : null)
                .nrRuote(bi.getNrRuote())
                .numeroMarce(bi.getNumeroMarce())
                .pieghevole(bi.getPieghevole())
                .tipoFreno(bi.getTipoFreno() != null ? 
                        bi.getTipoFreno().getDescrizione() : null)
                .tipoSospensione(bi.getTipoSospensione() != null ? 
                        bi.getTipoSospensione().getDescrizione() : null)
				.build();
	}
}
