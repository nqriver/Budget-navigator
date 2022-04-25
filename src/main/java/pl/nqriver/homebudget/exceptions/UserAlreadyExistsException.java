package pl.nqriver.homebudget.exceptions;

import pl.nqriver.homebudget.enums.AuthenticationMessageEnum;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super(AuthenticationMessageEnum.USER_ALREADY_EXISTS.getMessage());
    }

}
