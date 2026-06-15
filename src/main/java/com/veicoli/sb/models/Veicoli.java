package com.veicoli.sb.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
name = "tipo_veicolo")
public class Veicoli {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
//	@Column(
//			name = "tipo_v",
//			nullable = false
//			)
//	protected String tipoVeicolo;
	
	@Column(
			name = "colore",
			nullable = false
			)
	private String colore;
	
	@Column(
			name = "marca",
			nullable = false
			)
	private String marca;
	
	@Column(
			name = "anno_p",
			nullable = false
			)
	private Integer annoProduzione;
	
	@Column(
			name = "modello",
			nullable = false
			)
	private String modello;
}
