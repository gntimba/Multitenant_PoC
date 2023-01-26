package com.eskom.back.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorType> handleNotFound(NotFoundException nfe){

        return new ResponseEntity<ErrorType>(
                new ErrorType(
                        new Date(System.currentTimeMillis()).toString(),
                        "404- NOT FOUND",
                        nfe.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFound.class)
    public ResponseEntity<ErrorType> handleNotFound(MemberNotFound nfe){

        return new ResponseEntity<ErrorType>(
                new ErrorType(
                        new Date(System.currentTimeMillis()).toString(),
                        "404- The member is not found",
                        nfe.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(GroupNotFound.class)
    public ResponseEntity<ErrorType> handleNotFound(GroupNotFound nfe){

        return new ResponseEntity<ErrorType>(
                new ErrorType(
                        new Date(System.currentTimeMillis()).toString(),
                        "404- The group is not found",
                        nfe.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NotUnique.class)
    public ResponseEntity<ErrorType> NotUnique(NotUnique nfe){

        return new ResponseEntity<ErrorType>(
                new ErrorType(
                        new Date(System.currentTimeMillis()).toString(),
                        "400- it exists",
                        nfe.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
