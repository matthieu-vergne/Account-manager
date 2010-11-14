package manager;

@SuppressWarnings("serial")
public class AlreadyAppliedMovementException extends RuntimeException {
    public AlreadyAppliedMovementException() {
        super("this movement is already applied");
    }
}
