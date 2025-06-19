package com.learnandcode.news_aggregator.advice;

import com.learnandcode.news_aggregator.dto.ApiErrorResponse;
import com.learnandcode.news_aggregator.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidEmailException(InvalidEmailException ex){
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiErrorResponse> handlerInvalidPasswordException(InvalidPasswordException ex){
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidUsernameException(InvalidUsernameException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.CONTINUE.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExternalServerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleExternalServerNotFoundException(ExternalServerNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateSavedArticleException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateSavedArticleException(DuplicateSavedArticleException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SavedArticleNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleSavedArticleNotFoundException(SavedArticleNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}


