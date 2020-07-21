package com.ea.eadp.product.product.exception;

import com.ea.eadp.product.product.model.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NullPointerException.class, ProductServiceException.class})
    public ResponseEntity<Object> handleSpecificException(Exception ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductMissingException.class)
    public ResponseEntity<Object> handleProductMissingException(Exception ex, WebRequest request) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null) errorMessageDescription = ex.toString();

        ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }




}
