package com.veicoli.sb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veicoli.sb.models.TipoAlimentazione;

public interface ITipoAlimentazioneRepository extends JpaRepository<TipoAlimentazione, Integer> {
    
	List<TipoAlimentazione> findByTipoVeicolo(String tipoVeicolo);
}
