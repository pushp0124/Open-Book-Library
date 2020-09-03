package com.obl.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class OpenBookLibraryExceptionHandling {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<OpenBookLibraryException> handleAllOtherErrors(OpenBookLibraryException exception) {
    	System.out.println(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(exception);
    }

}