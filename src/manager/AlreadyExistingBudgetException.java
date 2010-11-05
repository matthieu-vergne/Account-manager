package manager;

@SuppressWarnings("serial")
public class AlreadyExistingBudgetException extends RuntimeException {

	public AlreadyExistingBudgetException() {
		super("another budget with the same name already exists");
	}

	public AlreadyExistingBudgetException(String budgetName) {
		super("another budget with the name '" + budgetName
				+ "' already exists");
	}
}
