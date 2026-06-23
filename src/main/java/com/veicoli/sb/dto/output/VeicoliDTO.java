package com.veicoli.sb.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VeicoliDTO {
	private Integer id;
	private String tipoVeicolo;
	private Integer annoProduzione;
	private String colore;
	private String marca;
	private String modello;
	private String tipoAlimentazione;
	private String codiceTarga;
	private String categoria;
	private String targa;
}