package com.veicoli.sb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veicoli.sb.models.TipoAlimentazione;

public interface ITipoAlimentazioneRepository extends JpaRepository<TipoAlimentazione, Integer> {
    
	Optional<TipoAlimentazione> findByIdAndTipoVeicolo(Integer id, String tipoVeicolo);
}
