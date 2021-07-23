package com.training.springbootusecase.util;

import com.training.springbootusecase.exceptions.AuthenticationException;
import com.training.springbootusecase.exceptions.BusNotFoundException;
import com.training.springbootusecase.exceptions.DuplicateUserException;
import com.training.springbootusecase.exceptions.ErrorDto;
import com.training.springbootusecase.exceptions.FieldErrorDto;
import com.training.springbootusecase.exceptions.FundTransferDownException;
import com.training.springbootusecase.exceptions.NoArgumentException;
import com.training.springbootusecase.exceptions.NoSuchUserException;
import com.training.springbootusecase.exceptions.TicketNotFoundException;
import com.training.springbootusecase.exceptions.TransactionFailedException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class BusServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    protected ResponseEntity<ErrorDto> handleWhileDuplicateEmail(DuplicateUserException e){

        return new ResponseEntity<>(ErrorDto
                .builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build(), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(NoSuchUserException.class)
    protected ResponseEntity<ErrorDto> handleWhileUserNotFound(NoSuchUserException e){

        return new ResponseEntity<>(ErrorDto
                .builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TicketNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleWhileTicketsNotFound(NoSuchUserException e){

        return new ResponseEntity<>(ErrorDto
                .builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransactionFailedException.class)
    protected ResponseEntity<ErrorDto> handleWhenTransactionFailed(RuntimeException e){
        return new ResponseEntity<>(ErrorDto
                .builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ErrorDto> handleWhenPasswordIsWrong(RuntimeException e){
        return new ResponseEntity<>(ErrorDto
                .builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BusNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleWhenBusIsNotFound(RuntimeException e){
        return new ResponseEntity<>(ErrorDto
                .builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoArgumentException.class)
    protected ResponseEntity<ErrorDto> handleWhenArgumentIsNotGiven(RuntimeException e){
        return new ResponseEntity<>(ErrorDto
                .builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FundTransferDownException.class)
    protected ResponseEntity<ErrorDto> handleWhenFundTransferIsDown(RuntimeException e){
        return new ResponseEntity<>(ErrorDto
                .builder()
                .errorCode(500)
                .errorMessage(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<FieldErrorDto> handleWhileValidating(ConstraintViolationException e){

        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> error : e.getConstraintViolations()) {
            errors.add(error.getMessage());
        }
        return new ResponseEntity<>(FieldErrorDto
                .builder()
                .errorCode(500)
                .errorMessage("Field error")
                .errors(errors)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(FeignException.class)
    protected ResponseEntity<String> handleWhenErrorInFundTransfer(FeignException e){
        return new ResponseEntity<>(e.contentUTF8(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}