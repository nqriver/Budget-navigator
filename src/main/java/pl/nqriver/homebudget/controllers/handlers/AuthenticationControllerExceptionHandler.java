package pl.nqriver.homebudget.controllers.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.nqriver.homebudget.controllers.handlers.dtos.ErrorMessage;
import pl.nqriver.homebudget.exceptions.InvalidUsernameOrPasswordException;


@RestControllerAdvice
public class AuthenticationControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage incorrectUsernameOrPasswordExceptionHandler(InvalidUsernameOrPasswordException exception) {
        return ErrorMessage.builder()
                .errorCode(HttpStatus.FORBIDDEN.toString())
                .errorDescription(exception.getMessage())
                .build();
    }
}
