package com.store.app.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {

  private int status;
  private String message;
  private List<String> errors;
}
