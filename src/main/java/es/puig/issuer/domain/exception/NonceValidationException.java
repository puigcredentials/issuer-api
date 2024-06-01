package es.puig.issuer.domain.exception;

public class NonceValidationException extends Exception {
    public NonceValidationException(String message) {
        super(message);
    }
}
