package manager;

@SuppressWarnings("serial")
public class AlreadyExistingAccountException extends RuntimeException {

	public AlreadyExistingAccountException() {
		super("another account with the same name already exists");
	}

	public AlreadyExistingAccountException(String accountName) {
		super("another account with the name '" + accountName
				+ "' already exists");
	}
}
