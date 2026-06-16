package com.veicoli.sb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veicoli.sb.models.Categoria;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer>{

}
