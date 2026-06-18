package com.veicoli.sb.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veicoli.sb.services.interfaces.IMotoServices;
import com.veicoli.sb.services.interfaces.IVeicoliServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/veicoli")
public class VeicoliController {

	private final IVeicoliServices veS;
	

	@GetMapping("/list")
	public ResponseEntity<Object> list()throws Exception{
		return ResponseEntity.ok(veS.list());
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<Object> search(
	        @RequestParam(required = false) String tipoVeicolo,
	        @RequestParam(required = false) String marca,
	        @RequestParam(required = false) String modello,
	        @RequestParam(required = false) String colore,
	        @RequestParam(required = false) Integer annoProduzione,
	        @RequestParam(required = false) String tipoAlimentazione,
	        @RequestParam(required = false) String categoria,
	        @RequestParam(required = false) String targa) throws Exception {
	    return ResponseEntity.ok(
	        veS.search(tipoVeicolo, marca, modello, colore, annoProduzione, tipoAlimentazione, categoria, targa)
	    );
	}
}
