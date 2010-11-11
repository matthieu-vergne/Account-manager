package manager;

@SuppressWarnings("serial")
public class UnknownBudgetException extends RuntimeException {

	public UnknownBudgetException() {
		super("the given budget is not in the manager");
	}

	public UnknownBudgetException(String budgetName) {
		super("the budget '" + budgetName + "' is not in the manager");
	}
}
