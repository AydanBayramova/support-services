package az.finalproject.msrating.exception;

public class RatingAlreadyExistsException extends RuntimeException {
    public RatingAlreadyExistsException(String message) {
        super(message);
    }
}
