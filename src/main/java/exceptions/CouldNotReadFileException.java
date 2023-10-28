package exceptions;

/**
 * An exception stating that the file cannot be read.
 */
public class CouldNotReadFileException extends RuntimeException {
    public CouldNotReadFileException(String message) {
        super(message);
    }
}
