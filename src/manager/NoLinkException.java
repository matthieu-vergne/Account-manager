package manager;

@SuppressWarnings("serial")
public class NoLinkException extends RuntimeException {

	public NoLinkException() {
		super("there is no link between the given account and budget");
	}

	public NoLinkException(String accountName, String budgetName) {
		super("there is no link between the account '" + accountName
				+ "' and budget '" + budgetName + "'");
	}
}
