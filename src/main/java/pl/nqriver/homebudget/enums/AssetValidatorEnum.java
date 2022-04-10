package pl.nqriver.homebudget.enums;

public enum AssetValidatorEnum {
    NO_AMOUNT("No amount supplied");

    private final String message;

    AssetValidatorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
