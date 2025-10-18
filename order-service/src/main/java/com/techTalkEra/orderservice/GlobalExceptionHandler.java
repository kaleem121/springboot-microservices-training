package com.techTalkEra.orderservice;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	  public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex, HttpServletRequest req) {
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(base(HttpStatus.NOT_FOUND, ex.getMessage(), req));
	  }


	 @ExceptionHandler(MethodArgumentNotValidException.class)
	  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
	    Map<String, Object> body = base(HttpStatus.BAD_REQUEST, "Validation failed", req);
	    body.put("details", ex.getBindingResult().getFieldErrors().stream().map(fe ->
	      Map.of("field", fe.getField(), "rejected", fe.getRejectedValue(), "message", fe.getDefaultMessage())
	    ).toList());
	    return ResponseEntity.badRequest().body(body);
	  }
	 
	 @ExceptionHandler(ConstraintViolationException.class)
	  public ResponseEntity<Map<String, Object>> constraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
	    Map<String, Object> body = base(HttpStatus.BAD_REQUEST, "Constraint violation", req);
	    body.put("details", ex.getConstraintViolations().stream().map(v ->
	      Map.of("property", v.getPropertyPath().toString(), "message", v.getMessage())
	    ).toList());
	    return ResponseEntity.badRequest().body(body);
	  }
	 
	 @ExceptionHandler(HttpMessageNotReadableException.class)
	  public ResponseEntity<Map<String, Object>> badJson(HttpMessageNotReadableException ex, HttpServletRequest req) {
	    return ResponseEntity.badRequest().body(base(HttpStatus.BAD_REQUEST, "Malformed JSON request", req));
	  }
	 
	 @ExceptionHandler(MethodArgumentTypeMismatchException.class)
	  public ResponseEntity<Map<String, Object>> typeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
	    return ResponseEntity.badRequest().body(base(HttpStatus.BAD_REQUEST, "Invalid value for parameter '%s'".formatted(ex.getName()), req));
	  }
	    
	    
	    private Map<String, Object> base(HttpStatus status, String message, HttpServletRequest req) {
	        Map<String, Object> body = new LinkedHashMap<>();
	        body.put("timestamp", Instant.now().toString());
	        body.put("status", status.value());
	        body.put("error", status.getReasonPhrase());
	        body.put("message", message);
	        body.put("path", req.getRequestURI());
	        return body;
	      }
	    
	    @ExceptionHandler(ProductNotFoundException.class)
	    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e){
	    	
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    	
	    }
	    
	    @ExceptionHandler(ProductServiceDownException.class)
	    public ResponseEntity<String> handleProductServiceDown(ProductServiceDownException e){
	    	return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(e.getMessage());
	    }
	    
	    @ExceptionHandler(FeignGenericException.class)
	    public ResponseEntity<String> handleGeneralException(FeignGenericException e){
	    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
}
