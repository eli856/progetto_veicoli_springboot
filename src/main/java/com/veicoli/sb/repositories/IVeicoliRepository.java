package com.veicoli.sb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.veicoli.sb.models.Veicoli;

@Repository
public interface IVeicoliRepository extends JpaRepository<Veicoli, Integer>{

	
	@Query(name= "search.query")
	List<Veicoli> search(
			@Param("tipoVeicolo") String tipoVeicolo,
            @Param("marca") String marca,
            @Param("modello") String modello,
            @Param("colore") String colore,
            @Param("annoProduzione") Integer annoProduzione,
            @Param("tipoAlimentazione") String tipoAlimentazione,
            @Param("categoria") String categoria,
            @Param("targa") String targa
			);
}
