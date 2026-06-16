package com.veicoli.sb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veicoli.sb.models.Bici;

public interface IBiciRepository extends JpaRepository<Bici, Integer>{

}
