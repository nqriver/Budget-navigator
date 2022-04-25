package pl.nqriver.homebudget.exceptions;

import pl.nqriver.homebudget.enums.AuthenticationMessageEnum;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super(AuthenticationMessageEnum.USER_NOT_FOUND.getMessage());
    }

}
