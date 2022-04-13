package pl.nqriver.homebudget.exceptions;

import lombok.Getter;

@Getter
public class AssetIncompleteException extends RuntimeException {

    private final String errorCode;
    public AssetIncompleteException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
