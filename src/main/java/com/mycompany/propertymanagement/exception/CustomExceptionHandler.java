package com.mycompany.propertymanagement.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorModel>> handleFieldValidation(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<ErrorModel> errorModels = new ArrayList<>();
        ErrorModel errorModel = null;
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        for (FieldError fieldError: fieldErrors) {
            logger.info("Inside field validation: {} - {}", fieldError.getField(), fieldError.getDefaultMessage());
            errorModel = new ErrorModel();
            errorModel.setCode(fieldError.getField());
            errorModel.setMessage(fieldError.getDefaultMessage());
            errorModels.add(errorModel);
        }

        return new ResponseEntity<List<ErrorModel>>(errorModels, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<List<ErrorModel>> handleBusinessException(BusinessException businessException) {
        for (ErrorModel errorModel: businessException.getErrorModels()) {
            logger.info("BusinessException is thrown: {} - {}", errorModel.getCode(), errorModel.getMessage());
        }
        return new ResponseEntity<List<ErrorModel>>(businessException.getErrorModels(), HttpStatus.BAD_REQUEST);
    }
}
