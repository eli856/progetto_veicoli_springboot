package com.veicoli.sb.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class BiciDTO {
	private Integer id;
    private String colore;
    private String marca;
    private Integer annoProduzione;
    private String modello;
    private String tipoAlimentazione;
    private String categoria;
    private Integer nrRuote;
    private Integer numeroMarce;
    private Boolean pieghevole;
    private String tipoFreno;
    private String tipoSospensione;
}
