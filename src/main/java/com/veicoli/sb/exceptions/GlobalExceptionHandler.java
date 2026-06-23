package com.veicoli.sb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.veicoli.sb.dto.output.ResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
		String msg = ex.getBindingResult().getFieldErrors().stream()
				.findFirst()
				.map(fe -> fe.getDefaultMessage())
				.orElse("Validation error");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ResponseDTO.builder().msg(msg).build());
	}

	@ExceptionHandler(VeicoliExceptions.class)
	public ResponseEntity<ResponseDTO> handleVeicoliException(VeicoliExceptions ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ResponseDTO.builder().msg(ex.getMessage()).build());
	}
}
