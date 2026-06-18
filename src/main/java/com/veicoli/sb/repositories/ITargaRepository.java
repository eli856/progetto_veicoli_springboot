package com.veicoli.sb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veicoli.sb.models.Targa;

public interface ITargaRepository extends JpaRepository<Targa, Integer>{
	
	boolean existsByCodice(String codice);
    boolean existsByCodiceAndIdNot(String codice, Integer id);
}
