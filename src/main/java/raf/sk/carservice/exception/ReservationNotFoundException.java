package raf.sk.carservice.exception;

public class ReservationNotFoundException extends RuntimeException{

    private String message;

    public ReservationNotFoundException(String message){
        super(message);
    }
}
