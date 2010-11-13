package manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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

	/**
	 * The list of links between accounts and budgets. It is a set because it is
	 * too complex to manage several links on the same account and budget and no
	 * specific advantages. Prefer to change the links possibilities instead of
	 * make some strange linking.
	 */
	final private Set<Link> links = new HashSet<Link>();

	/**
	 * Link an account and a budget which are not yet linked.
	 * 
	 * @param accountName
	 *            the name of the account to link to the budget
	 * @param budgetName
	 *            the name of the budget to link to the account
	 * @exception ExistingLinkException
	 *                if the link already exists
	 */
	public void links(String accountName, String budgetName) {
		Link link = generateLink(accountName, budgetName);
		if (links.contains(link)) {
			throw new ExistingLinkException();
		}
		links.add(link);
	}

	/**
	 * A link allows an account to food a budget. If there is no value (null)
	 * the budget can take what it needs (depending of its links with other
	 * accounts and the possibilities of this one), otherwise this link is fixed
	 * to an amount of money, reserved for this budget.
	 * 
	 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
	 * 
	 */
	class Link {
		/**
		 * The account which give the money to the budget.
		 */
		final Account account;
		/**
		 * The budget which use the money of the account.
		 */
		final Budget budget;
		/**
		 * The value of this link. If null, the amount of money shared by this
		 * link is managed by the manager, otherwise the given value indicate
		 * the amount of money of the account reserved to the budget.
		 */
		BigDecimal value;

		public Link(Account account, Budget budget) {
			this.account = account;
			this.budget = budget;
			this.value = null;
		}

		/**
		 * 
		 * @param element
		 *            an account or a budget
		 * @return true if the link manage the given element, false otherwise
		 */
		public <T extends AccountancyElement> boolean linksTheElement(T element) {
			return (element instanceof Account) ? account.equals(element)
					: budget.equals(element);
		}

		/**
		 * Two links are equal if they have the same account and budget couple.
		 * There is no value check, two links with different values are the same
		 * link if they link the same account to the same budget.
		 */
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Link) {
				Link l = (Link) obj;
				return account == l.account && budget == l.budget;
			}
			throw new IllegalArgumentException("the argument is not a "
					+ getClass());
		}

		/**
		 * Give a hashcode depending of account and budget, so a set of links
		 * can recognize two equal links by their hashcode. This is especially
		 * necessary for {@link Set#contains(Object)}.
		 */
		@Override
		public int hashCode() {
			return account.hashCode() + budget.hashCode();
		}
	}

	/**
	 * Tell if the account and the budgets are linked
	 * 
	 * @param accountName
	 *            the name of the account
	 * @param budgetName
	 *            the name of the budget
	 * @return true if there is already a link between them, false otherwise
	 */
	public boolean isLinked(String accountName, String budgetName) {
		return links.contains(generateLink(accountName, budgetName));
	}

	/**
	 * Give the value of the link between the given account and budget.
	 * 
	 * @param accountName
	 *            the name of the account
	 * @param budgetName
	 *            the name of the budget
	 * @return the value of the link, null if there is no constraint (the
	 *         manager manage it itself)
	 */
	public BigDecimal getLinkValue(String accountName, String budgetName) {
		Link link = getLink(accountName, budgetName);
		return link.value;
	}

	/**
	 * Give the existing link which correspond to the given account and budget.
	 * 
	 * @param accountName
	 *            the name of the account linked to the budget
	 * @param budgetName
	 *            the name of the budget linked to the account
	 * @return the corresponding link known by the manager
	 * @exception LinkException
	 *                if the link does not exist
	 */
	private Link getLink(String accountName, String budgetName) {
		Link template = generateLink(accountName, budgetName);
		for (Link link : links) {
			if (link.equals(template)) {
				return link;
			}
		}
		throw new NoLinkException(accountName, budgetName);
	}

	/**
	 * Generate a link, without insert it in the manager. This method manage all
	 * the checking needed to create a new link.
	 * 
	 * @param accountName
	 *            the name of an account of this manager
	 * @param budgetName
	 *            the name of a budget of this manager
	 * @return a new link between the account and the budget
	 * @exception UnknownAccountException
	 *                if the given account is not known by the manager
	 * @exception UnknownBudgetException
	 *                if the given budget is not known by the manager
	 */
	private Link generateLink(String accountName, String budgetName) {
		Account account = getAccount(accountName);
		if (account == null) {
			throw new UnknownAccountException(accountName);
		}

		Budget budget = getBudget(budgetName);
		if (budget == null) {
			throw new UnknownBudgetException(budgetName);
		}

		return new Link(account, budget);
	}

	/**
	 * Give a new value to the link between the given account and budget.
	 * 
	 * @param accountName
	 *            the name of the account linked to the budget
	 * @param budgetNamethe
	 *            name of the budget linked to the account
	 * @param newValue
	 *            the value to apply to the link
	 */
	public void changeLinkValue(String accountName, String budgetName,
			BigDecimal newValue) {
		Link link = getLink(accountName, budgetName);
		// TODO check if there is a need to control value sign
		// basically a negative value should be forbidden, as a negative value
		// means the budget feed the account, what is a non-sense
		link.value = newValue;
	}

	/**
	 * 
	 * @param budgetName
	 *            the name of a budget
	 * @return the names of the accounts linked to the budget
	 */
	public String[] getAccountsLinkedToBudget(String budgetName) {
		return getElementsLinkedToElement(getBudget(budgetName));
	}

	/**
	 * 
	 * @param accountName
	 *            the name of an account
	 * @return the names of the budgets linked to the account
	 */
	public String[] getBudgetsLinkedToAccount(String accountName) {
		return getElementsLinkedToElement(getAccount(accountName));
	}

	/**
	 * Give the names of the elements (accounts or budgets) linked to the given
	 * element (respectively budget or account).
	 * 
	 * @param element
	 *            the element to check
	 * @return the names of the elements linked to the element in argument
	 */
	private <T extends AccountancyElement> String[] getElementsLinkedToElement(
			T element) {
		List<String> linkedNames = new ArrayList<String>();
		for (Link link : links) {
			if (link.linksTheElement(element)) {
				AccountancyElement linkedElement = element instanceof Budget ? link.account
						: link.budget;
				linkedNames.add(linkedElement.getName());
			}
		}
		String[] array = linkedNames.toArray(new String[] {});
		Arrays.sort(array);
		return array;
	}

}
