package pl.nqriver.homebudget.exceptions;

import pl.nqriver.homebudget.enums.AuthenticationMessageEnum;

public class InvalidUsernameOrPasswordException extends RuntimeException {
    public InvalidUsernameOrPasswordException() {
        super(AuthenticationMessageEnum.INVALID_USERNAME_OR_PASSWORD.getMessage());
    }
}
