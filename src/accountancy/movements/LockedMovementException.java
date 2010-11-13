package accountancy.movements;

@SuppressWarnings("serial")
public class LockedMovementException extends RuntimeException {

	public LockedMovementException() {
		super("the movement is locked, no modification can be done");
	}
}
