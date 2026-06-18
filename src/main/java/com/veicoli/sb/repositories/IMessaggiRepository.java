package com.veicoli.sb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veicoli.sb.models.MessageID;
import com.veicoli.sb.models.Messaggi;


public interface IMessaggiRepository extends JpaRepository<Messaggi, MessageID>{

}
