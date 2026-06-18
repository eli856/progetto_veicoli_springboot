package com.veicoli.sb.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BiciReq {
	
	@NotNull(groups = ValidationGroups.Update.class, message = "id obbligatorio per update")
	private Integer id;
	
	// veicoli table
    @NotNull (groups = ValidationGroups.Create.class, message ="colore non caricato")
	@NotBlank(groups = ValidationGroups.Create.class, message ="colore non caricato")
    private String colore;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="marca non caricato")
	@NotBlank(groups = ValidationGroups.Create.class, message ="marca non caricato")
    private String marca;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="annoProduzione non caricato")
    private Integer annoProduzione;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="modello non caricato")
	@NotBlank(groups = ValidationGroups.Create.class, message ="modello non caricato")
    private String modello;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="id Alimentazione non caricato")
    private Integer idAlimentazione;   // FK → tipo_alimentazione
    
    @NotNull (groups = ValidationGroups.Create.class, message ="id Categoria non caricato")
    private Integer idCategoria;       // FK → categoria
    
 // fields from bici table
//    @NotNull (groups = ValidationGroups.Create.class, message ="numero Ruote non caricato")
//    private Integer nrRuote;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="numero Marce Ruote non caricato")
    private Integer numeroMarce;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="pieghevole non caricato")
    private Boolean pieghevole;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="tipo Freno non caricato")
    private Integer tipoFreno;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="tipo Sospensione non caricato")
    private Integer tipoSospensione;

}
