package com.veicoli.sb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veicoli.sb.models.Categoria;
import com.veicoli.sb.models.TipoAlimentazione;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{

	Optional<Categoria> findByIdAndTipoVeicolo(Integer id, String tipoVeicolo);
}
