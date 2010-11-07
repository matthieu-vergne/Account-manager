package manager;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import accountancy.AccountancyElement;
import accountancy.accounts.Account;
import accountancy.budgets.Budget;

/**
 * A manager is a container for accounts and budgets. It offers several methods
 * to link them and do some calculating.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class Manager {

	/**
	 * The list of accounts, sorted by name.
	 */
	private final Set<Account> accounts = new TreeSet<Account>(
			new Comparator<Account>() {
				@Override
				public int compare(Account a1, Account a2) {
					return a1.getName().compareTo(a2.getName());
				}
			});
	/**
	 * The list of budgets, sorted by name.
	 */
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

	/**
	 * @exception AlreadyExistingAccountException
	 *                if the given account has the same name than another
	 *                already in this manager
	 */
	public void addAccount(Account newAccount) {
		if (!accounts.add(newAccount)) {
			throw new AlreadyExistingAccountException();
		}
	}

	/**
	 * @exception AlreadyExistingBudgetException
	 *                if the given budget has the same name than another already
	 *                in this manager
	 */
	public void addBudget(Budget newBudget) {
		if (!budgets.add(newBudget)) {
			throw new AlreadyExistingBudgetException();
		}
	}

	/**
	 * @return The account which has the given name (in this manager), null
	 *         otherwise
	 */
	public Account getAccount(String accountName) {
		return getElement(accounts, accountName);
	}

	/**
	 * @return The budget which has the given name (in this manager), null
	 *         otherwise
	 */
	public Budget getBudget(String budgetName) {
		return getElement(budgets, budgetName);
	}

	/**
	 * @return The element of the given list which has the given name, null
	 *         otherwise
	 */
	private <T extends AccountancyElement> T getElement(Set<T> list,
			String elementName) {
		if (list == null) {
			throw new NullPointerException("the list cannot be null");
		}
		
		for (Iterator<T> iterator = list.iterator(); iterator.hasNext();) {
			T element = iterator.next();
			if (element.getName().equals(elementName)) {
				return element;
			}
		}
		return null;
	}

}
