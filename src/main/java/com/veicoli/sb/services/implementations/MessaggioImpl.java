package com.veicoli.sb.services.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.veicoli.sb.models.MessageID;
import com.veicoli.sb.models.Messaggi;
import com.veicoli.sb.repositories.IMessaggiRepository;
import com.veicoli.sb.services.interfaces.IMessaggioServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessaggioImpl implements IMessaggioServices {

	private final IMessaggiRepository msgR;
	@Value("${lang}")
	private String lang;

	@Override
	public String get(String code) {
		log.debug("get {}", code);
		String r = null;
		Optional<Messaggi> m = msgR.findById(new MessageID(lang, code));
		if (m.isEmpty())
			r = code;
		else
			r = m.get().getMessagio();

		return r;
	}

}
