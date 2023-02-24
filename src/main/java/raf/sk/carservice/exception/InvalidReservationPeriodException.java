package raf.sk.carservice.exception;

public class InvalidReservationPeriodException extends RuntimeException{

    private String message;

    public InvalidReservationPeriodException(String message){
        super(message);
    }
}
