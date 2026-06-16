package com.veicoli.sb.dto.input;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MacchinaReq {
	
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

    // fields from macchina table
    @NotNull (groups = ValidationGroups.Create.class, message ="numero Ruote non caricato")
    private Integer nrRuote;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="cc non caricato")
    private Integer cc;
    
    @NotNull (groups = ValidationGroups.Create.class, message ="numero Porte non caricato")
    private Integer numeroPorte;

    // targa
    @NotNull (groups = ValidationGroups.Create.class, message ="codice Targa non caricato")
	@NotBlank(groups = ValidationGroups.Create.class, message ="codice Targa non caricato")
    private String codiceTarga;
}
