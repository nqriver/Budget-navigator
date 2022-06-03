package pl.nqriver.homebudget.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Resource cannot be found");
    }
}
