package raf.sk.carservice.exception;

public class CarNotFoundException extends RuntimeException{

    private String message;

    public CarNotFoundException(String message) {
        super(message);
    }
}
