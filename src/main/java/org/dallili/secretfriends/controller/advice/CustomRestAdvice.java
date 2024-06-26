package org.dallili.secretfriends.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.dallili.secretfriends.dto.HttpErrorResponse;
import org.dallili.secretfriends.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomRestAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorResponse exceptionHandler(Exception ex, HttpServletRequest request){

        return HttpErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getClass().getName())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(CustomException.class)
    public HttpErrorResponse exceptionHandler(CustomException e, HttpServletRequest request){
        return HttpErrorResponse.builder()
                .status(e.getErrorCode().getStatus())
                .message(e.getErrorCode().getMessage())
                .error(e.getClass().getName())
                .path(request.getRequestURI())
                .build();
    }
}
