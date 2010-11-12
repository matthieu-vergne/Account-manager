package accountancy.movements;

@SuppressWarnings("serial")
public class MovementExceededValueException extends RuntimeException {

	public MovementExceededValueException() {
		super("you have tried to assign more than expected");
	}
}
