package pl.nqriver.homebudget.enums;

public enum AssetValidatorEnum {
    NO_AMOUNT("No amount specified"),
    NO_INCOME_DATE("No income date specified");

    private final String message;

    AssetValidatorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
