package com.veicoli.sb.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MacchinaDTO {
   
	private Integer id;
    private String colore;
    private String marca;
    private Integer annoProduzione;
    private String modello;
    private String tipoAlimentazione;
    private String categoria;
    private Integer nrRuote;
    private Integer cc;
    private Integer numeroPorte;
    private String codiceTarga;
}
