package it.marconi.biblioteca.controllers;

import it.marconi.biblioteca.domain.generics.APIResponse;
import it.marconi.biblioteca.domain.generics.APIResponseStatus;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<APIResponse<Object>> handleGeneric(Exception ex){
        APIResponse<Object> res = APIResponse.builder()
                .status(APIResponseStatus.ERROR)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<APIResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {
        APIResponse<Object> res = APIResponse.builder()
                .status(APIResponseStatus.ERROR)
                .message(ex.getReason())
                .build();

        return new ResponseEntity<>(res, ex.getStatusCode());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors =
                ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));

        APIResponse<Object> res = APIResponse.builder()
                .status(APIResponseStatus.ERROR)
                .message(ex.getMessage())
                .data(errors)
                .build();

        return new ResponseEntity<>(res, ex.getStatusCode());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse<Object>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        APIResponse<Object> res = APIResponse.builder()
                .status(APIResponseStatus.ERROR)
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}
