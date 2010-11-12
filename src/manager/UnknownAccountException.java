package manager;

@SuppressWarnings("serial")
public class UnknownAccountException extends RuntimeException {

	public UnknownAccountException() {
		super("the given account is not in the manager");
	}

	public UnknownAccountException(String accountName) {
		super("the account '" + accountName + "' is not in the manager");
	}
}
