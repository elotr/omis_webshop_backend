package com.elo.webshopbackend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
import java.util.NoSuchElementException;

@ControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<HttpExceptionResponse> handleException(EmptyResultDataAccessException e) {
        HttpExceptionResponse response = new HttpExceptionResponse(
                new Date(),
                "ID liiga suur v]i v'ike",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<HttpExceptionResponse> handleException(MethodArgumentTypeMismatchException e) { // errori nime saan konsoolist
        HttpExceptionResponse response = new HttpExceptionResponse(
                new Date(),
                "ID ei saa olla t2ht v6i muu symbol",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<HttpExceptionResponse> handleException(ItemNotFoundException e) { // errori nime saan konsoolist
        HttpExceptionResponse response = new HttpExceptionResponse(
                new Date(),
                "Eseme ID liiga suur v]i v'ike",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<HttpExceptionResponse> handleException(UserAlreadyExistsException e) {
        HttpExceptionResponse response = new HttpExceptionResponse( // seda klassi, mudelit v]ib kasutada ka siin
                new Date(),
                "Kasutaja on juba registreeritud",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<HttpExceptionResponse> handleException(TransactionSystemException e) { // errori nime saan konsoolist
        HttpExceptionResponse response = new HttpExceptionResponse( // seda vastust v]ib kasutada ka siin
                new Date(),
                "T'ida k]ik kohustuslikud v'ljad",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<HttpExceptionResponse> handleException(NoSuchElementException e) { // errori nime saan konsoolist
        HttpExceptionResponse response = new HttpExceptionResponse( // seda vastust v]ib kasutada ka siin
                new Date(),
                "Sellist kasutajat ei eksisteeri.",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<HttpExceptionResponse> handleException(DataIntegrityViolationException e) { // errori nime saan konsoolist
        HttpExceptionResponse response = new HttpExceptionResponse( // seda vastust v]ib kasutada ka siin
                new Date(),
                "Sellist kasutajat ei eksisteeri.",
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
