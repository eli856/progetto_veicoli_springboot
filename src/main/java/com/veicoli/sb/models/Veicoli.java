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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter 
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_veicolo", discriminatorType = DiscriminatorType.STRING)
public class Veicoli {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String colore;

    @Column(nullable = false)
    private String marca;

    @Column(name = "anno_produzione", nullable = false)
    private Integer annoProduzione;

    @Column(nullable = false)
    private String modello;
    
    @ManyToOne
    @JoinColumn(name = "id_alimentazione", nullable = false)
    private TipoAlimentazione tipoAlimentazione;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}
