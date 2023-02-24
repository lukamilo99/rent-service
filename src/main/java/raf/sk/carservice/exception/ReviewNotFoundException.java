package raf.sk.carservice.exception;

public class ReviewNotFoundException extends RuntimeException{

    private String message;

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
