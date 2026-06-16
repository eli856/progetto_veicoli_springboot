package com.veicoli.sb.models;

import jakarta.persistence.EmbeddedId;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="messagi_sistema")
public class Messaggi {

	@EmbeddedId
	private MessageID msgID;
	
	private String messagio;
}
