package com.telerikacademy.carservice.controllers.exception_handler;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExistsException;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(DatabaseItemNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorHandler onDatabaseItemNotFoundException(DatabaseItemNotFoundException e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorHandler onException(Exception e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ExceptionHandler(DatabaseItemAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ErrorHandler onDatabaseItemAlreadyExists(DatabaseItemAlreadyExistsException e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        return error;
    }

    @ExceptionHandler(DatabaseItemAlreadyDeletedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ErrorHandler onDatabaseItemAlreadyDeletedException(DatabaseItemAlreadyDeletedException e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        return error;
    }



}
