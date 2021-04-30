package com.example.axonbank.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlingCommandExecutionException {
    @ExceptionHandler(CommandExecutionException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public Map<String, Object> handler(CommandExecutionException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());
        return result;
    }
}
