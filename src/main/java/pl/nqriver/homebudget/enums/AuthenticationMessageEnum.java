package pl.nqriver.homebudget.enums;

public enum AuthenticationMessageEnum {

    USER_NOT_FOUND("User cannot be found"),
    USER_ALREADY_EXISTS("user already exists");

    private final String message;

    AuthenticationMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
