package com.telerikacademy.carservice.controllers.exception_handler;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExists;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(DatabaseItemNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView onDatabaseItemNotFoundException(DatabaseItemNotFoundException e) {
        ErrorHandler error = new ErrorHandler();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error.toString());
        mav.setViewName("error");
        return mav;
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

    @ExceptionHandler(DatabaseItemAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ErrorHandler onDatabaseItemAlreadyExists(DatabaseItemAlreadyExists e) {
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
