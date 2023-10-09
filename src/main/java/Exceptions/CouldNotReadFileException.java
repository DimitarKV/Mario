package Exceptions;

public class CouldNotReadFileException extends RuntimeException {
    public CouldNotReadFileException(String message) {
        super(message);
    }
}
