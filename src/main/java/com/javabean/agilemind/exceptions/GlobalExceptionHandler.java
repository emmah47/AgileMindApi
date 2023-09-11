package com.javabean.agilemind.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handlePermissionDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied :(");
    }

    @ExceptionHandler(InvalidRequirementsException.class)
    public ResponseEntity<String> handleInvalidRequirementException(InvalidRequirementsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter valid requirements.");
    }
}
