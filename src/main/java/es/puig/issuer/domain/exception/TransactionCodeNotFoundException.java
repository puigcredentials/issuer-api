package es.puig.issuer.domain.exception;

public class TransactionCodeNotFoundException extends Exception{
    public TransactionCodeNotFoundException(String message) {
        super(message);
    }
}
