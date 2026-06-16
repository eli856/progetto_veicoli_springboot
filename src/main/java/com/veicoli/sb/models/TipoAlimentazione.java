package com.veicoli.sb.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_alimentazione")
@Getter 
@Setter 
@NoArgsConstructor
public class TipoAlimentazione {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(nullable = false)
	    private String descrizione;

	    @Column(name = "tipo_veicolo", nullable = false)
	    private String tipoVeicolo; // "MACCHINA", "MOTO", "BICI"
}
