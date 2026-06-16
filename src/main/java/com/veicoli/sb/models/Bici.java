package com.veicoli.sb.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bici")
@Getter @Setter @NoArgsConstructor
@DiscriminatorValue("BICI")
public class Bici extends Veicoli {

    @Column(name = "nr_ruote", nullable = false)
    private Integer nrRuote;

    @Column(name = "numero_marce", nullable = false)
    private Integer numeroMarce;

    @Column(nullable = false)
    private Boolean pieghevole = false;

    @ManyToOne
    @JoinColumn(name = "id_freno", nullable = false)
    private TipoFreno tipoFreno;

    @ManyToOne
    @JoinColumn(name = "id_sospensione", nullable = false)
    private TipoSospensione tipoSospensione;
}