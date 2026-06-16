package com.veicoli.sb.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "moto")
@Getter @Setter @NoArgsConstructor
@DiscriminatorValue("MOTO")
public class Moto extends Veicoli {

    @Column(name = "nr_ruote", nullable = false)
    private Integer nrRuote = 2;

    @Column(nullable = false)
    private Integer cc;

    @OneToOne(mappedBy = "moto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Targa targa;
}
