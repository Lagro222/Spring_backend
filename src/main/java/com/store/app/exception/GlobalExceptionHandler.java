package com.store.app.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

  //handle validation exception
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
      .getFieldErrors()
      .stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .collect(Collectors.toList());
    ApiError apiError = new ApiError(400, "Validation error", errors);
    return ResponseEntity.badRequest().body(apiError);

  }

  //hnadle runtime exception
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
    ApiError error = new ApiError(404, ex.getMessage(), List.of());
    return ResponseEntity.status(404).body(error);
    
  }


}
