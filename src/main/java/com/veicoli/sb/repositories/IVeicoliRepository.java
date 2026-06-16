package com.veicoli.sb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veicoli.sb.models.Macchina;

public interface IVeicoliRepository extends JpaRepository<Macchina, Integer>{

}
