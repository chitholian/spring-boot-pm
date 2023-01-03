package com.cnsbd.jtrainpm.exception;

import com.cnsbd.jtrainpm.model.JsonResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
class ErrorHandlingControllerAdvice {
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    JsonResponse onConstraintValidationException(ConstraintViolationException e) {
        e.printStackTrace();
        Map<String, Object> err = new HashMap<>();
        for (ConstraintViolation<?> v : e.getConstraintViolations()) {
            err.put(v.getPropertyPath().toString(), v.getMessage());
        }
        return new JsonResponse(400, new Object() {
            @JsonProperty
            Map<String, Object> errors = err;
        });
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    JsonResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> err = new HashMap<>();
        for (FieldError v : e.getFieldErrors()) {
            err.put(v.getField(), v.getDefaultMessage());
        }
        return new JsonResponse(400, new Object() {
            @JsonProperty
            Map<String, Object> errors = err;
        });
    }

    @ExceptionHandler(value = {AuthFailedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    JsonResponse onAuthFailedException(AuthFailedException e) {
        return new JsonResponse(401, new Object() {
            @JsonProperty
            String error = e.getMessage();
        });
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    JsonResponse onEntityNotFoundException(EntityNotFoundException e) {
        return new JsonResponse(404, new Object() {
            @JsonProperty
            String error = e.getMessage();
        });
    }
}
