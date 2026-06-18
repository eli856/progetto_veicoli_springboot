package com.veicoli.sb.dto.input;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class VeicoliReq {
    private String tipoVeicolo;
    private String marca;
    private String modello;
    private String colore;
    private Integer annoProduzione;
    private String tipoAlimentazione;
    private String categoria;
    private String targa;
}
