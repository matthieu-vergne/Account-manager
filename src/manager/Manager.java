package manager;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import accountancy.accounts.Account;
import accountancy.budgets.Budget;

public class Manager {

	private final Set<Account> accounts = new TreeSet<Account>(
			new Comparator<Account>() {
				@Override
				public int compare(Account a1, Account a2) {
					return a1.getName().compareTo(a2.getName());
				}
			});
	private final Set<Budget> budgets = new TreeSet<Budget>(
			new Comparator<Budget>() {
				@Override
				public int compare(Budget b1, Budget b2) {
					return b1.getName().compareTo(b2.getName());
				}
			});

	public Set<Account> getAccounts() {
		return accounts;
	}

	public Set<Budget> getBudgets() {
		return budgets;
	}

	public void addAccount(Account newAccount) {
		if (!accounts.add(newAccount)) {
			throw new AlreadyExistingAccountException();
		}
	}

	public void addBudget(Budget newBudget) {
		if (!budgets.add(newBudget)) {
			throw new AlreadyExistingBudgetException();
		}
	}

}
