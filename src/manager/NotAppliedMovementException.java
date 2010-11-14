package manager;

@SuppressWarnings("serial")
public class NotAppliedMovementException extends RuntimeException {
    public NotAppliedMovementException() {
        super("this movement is not applied yet");
    }
}
