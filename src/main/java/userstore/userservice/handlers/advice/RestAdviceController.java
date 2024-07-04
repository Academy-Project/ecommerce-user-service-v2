package userstore.userservice.handlers.advice ;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import userstore.userservice.handlers.exceptions.MultipleErrorsException;
import userstore.userservice.handlers.exceptions.NotFoundException;
import userstore.userservice.responses.ResponseHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestAdviceController {
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
//        List<String> errorMessages = e.getConstraintViolations()
//                .stream()
//                .map(constraintViolation -> String.format("%s attribute %s", constraintViolation.getPropertyPath(), constraintViolation.getMessage()))
//                .collect(Collectors.toList());
//
//        return ResponseHandler.errorResponse(errorMessages, HttpStatus.UNPROCESSABLE_ENTITY);
//    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> responseException(ResponseStatusException e) {
        return ResponseHandler.errorResponse(
                Collections.singletonList(e.getReason()),
                (HttpStatus) e.getStatusCode()
        );
    }

    @ExceptionHandler(MultipleErrorsException.class)
    public ResponseEntity<Object> multipleErrorsException(MultipleErrorsException e) {
        return ResponseHandler.errorResponse(
                e.getErrors(),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException e) {
        return ResponseHandler.errorResponse(
                Collections.singletonList(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> notFoundException(Exception e) {
        return ResponseHandler.errorResponse(
                Collections.singletonList(e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
