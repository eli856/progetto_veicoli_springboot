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
@Table(name = "macchina")
@Getter @Setter @NoArgsConstructor
@DiscriminatorValue("MACCHINA")
public class Macchina extends Veicoli {

    @Column(name = "nr_ruote", nullable = false)
    private Integer nrRuote = 4;

    @Column(nullable = false)
    private Integer cc;

    @Column(name = "numero_porte", nullable = false)
    private Integer numeroPorte;

    @OneToOne(mappedBy = "macchina", cascade = CascadeType.ALL, orphanRemoval = true)
    private Targa targa;
}