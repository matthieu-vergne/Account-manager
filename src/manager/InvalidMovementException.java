package manager;

@SuppressWarnings("serial")
public class InvalidMovementException extends RuntimeException {
    public InvalidMovementException() {
        super("this movement is invalid, check its fields");
    }
}
