package com.veicoli.sb.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veicoli.sb.dto.input.MotoReq;
import com.veicoli.sb.dto.input.ValidationGroups;
import com.veicoli.sb.dto.output.ResponseDTO;
import com.veicoli.sb.services.interfaces.IMotoServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/moto")
public class MotoController {

	private final IMotoServices motS;
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create (@RequestBody (required = true) @Validated(ValidationGroups.Create.class) MotoReq req) throws Exception{
		motS.create(req);
		return ResponseEntity.ok(ResponseDTO.builder()
				.msg("moto created...")
				.build());
	}
	
	@PatchMapping("update")
	public ResponseEntity<ResponseDTO> update (@RequestBody (required = true) @Validated(ValidationGroups.Update.class) MotoReq req) throws Exception{
		motS.update(req);
		return ResponseEntity.ok(ResponseDTO.builder()
				.msg("moto updated...")
				.build());
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@RequestBody (required = true) Integer id) throws Exception{
		motS.delete(id);
	return ResponseEntity.ok(ResponseDTO.builder()
			.msg("moto deleted...")
			.build());
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> list()throws Exception{
		return ResponseEntity.ok(motS.list());
	}
	
	@GetMapping("getById")
	public ResponseEntity<Object> getById(@RequestParam (required = true) Integer id)throws Exception{
		return ResponseEntity.ok(motS.getById(id));
	}
}
