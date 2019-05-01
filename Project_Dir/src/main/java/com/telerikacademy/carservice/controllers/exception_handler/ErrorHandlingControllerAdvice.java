package com.telerikacademy.carservice.controllers.exception_handler;

import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyDeletedException;
import com.telerikacademy.carservice.exceptions.DatabaseItemAlreadyExistsException;
import com.telerikacademy.carservice.exceptions.DatabaseItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    private ModelAndView getModelAndView(ErrorHandler error) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", error.toString());
        mav.setViewName("error");
        return mav;
    }


    @ExceptionHandler(DatabaseItemNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ModelAndView onDatabaseItemNotFoundException(DatabaseItemNotFoundException e) {
        ErrorHandler error = new ErrorHandler();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());

        return getModelAndView(error);
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ModelAndView onException(Exception e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage("Have you paid the Internet?");

        return getModelAndView(error);
    }

    @ExceptionHandler(DatabaseItemAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ModelAndView onDatabaseItemAlreadyExists(DatabaseItemAlreadyExistsException e) {
        ErrorHandler error = new ErrorHandler();
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        return getModelAndView(error);
    }

    @ExceptionHandler(DatabaseItemAlreadyDeletedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ModelAndView onDatabaseItemAlreadyDeletedException(DatabaseItemAlreadyDeletedException e) {
        ErrorHandler error = new ErrorHandler();

        error.setStatus(HttpStatus.CONFLICT.value());
        error.setMessage(e.getMessage());

        return getModelAndView(error);
    }


}
