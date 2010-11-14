package manager;

@SuppressWarnings("serial")
public class UnknownMovementException extends RuntimeException {
    public UnknownMovementException() {
        super("this movement does not exists in this manager");
    }
}
