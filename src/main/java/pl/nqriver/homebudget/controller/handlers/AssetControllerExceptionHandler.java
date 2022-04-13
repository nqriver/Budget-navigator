package pl.nqriver.homebudget.controller.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.nqriver.homebudget.controller.handlers.dtos.ErrorMessage;
import pl.nqriver.homebudget.exceptions.AssetIncompleteException;

@RestControllerAdvice
public class AssetControllerExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorMessage assetIncompleteExceptionHandler(AssetIncompleteException exception) {
        return ErrorMessage.builder()
                .errorCode(exception.getErrorCode())
                .errorDescription(exception.getMessage())
                .build();
    }
}
