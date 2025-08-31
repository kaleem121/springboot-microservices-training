package com.techTalkEra.helloservice;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(NotFoundException.class)
	    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
	        Map<String, Object> body = new HashMap<>();
	        body.put("error", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
	        Map<String, Object> body = new HashMap<>();
	        body.put("error", "Validation failed");
	        body.put("details", ex.getBindingResult().getFieldErrors());
	        return ResponseEntity.badRequest().body(body);
	    }
}
