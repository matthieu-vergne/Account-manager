package manager;

import accountancy.AccountancyElement;
import accountancy.accounts.Account;
import accountancy.budgets.Budget;
import org.junit.Test;
import static org.junit.Assert.*;

public class ManagerTest {

	@Test
	public void accountsManagingTest() {
		Manager manager = new Manager();
		assertEquals(0, manager.getAccounts().size());

		Account a1 = new Account();
		a1.setName("1");
		Account a2 = new Account();
		a2.setName("2");
		Account a3 = new Account();
		a3.setName("3");
		manager.addAccount(a2);
		manager.addAccount(a3);
		manager.addAccount(a1);
		assertArrayEquals(new Object[] { a1, a2, a3 }, manager.getAccounts()
				.toArray());

		try {
			manager.addAccount(a1);
			fail("no exception thrown");
		} catch (AlreadyExistingAccountException ex) {
		}

		assertEquals(a1, manager.getAccount("1"));
		assertEquals(a2, manager.getAccount("2"));
		assertEquals(a3, manager.getAccount("3"));
		assertNull(manager.getAccount("4"));
	}

	@Test
	public void budgetsManagingTest() {
		Manager manager = new Manager();
		assertEquals(0, manager.getBudgets().size());

		Budget b1 = new Budget();
		b1.setName("1");
		Budget b2 = new Budget();
		b2.setName("2");
		Budget b3 = new Budget();
		b3.setName("3");
		manager.addBudget(b2);
		manager.addBudget(b3);
		manager.addBudget(b1);
		assertArrayEquals(new Object[] { b1, b2, b3 }, manager.getBudgets()
				.toArray());

		try {
			manager.addBudget(b1);
			fail("no exception thrown");
		} catch (AlreadyExistingBudgetException ex) {
		}

		assertEquals(b1, manager.getBudget("1"));
		assertEquals(b2, manager.getBudget("2"));
		assertEquals(b3, manager.getBudget("3"));
		assertNull(manager.getBudget("4"));
	}

	@Test
	public void linkingTest() {
		Manager manager = new Manager3by2();
		Account a1 = manager.getAccount("1");
		Account a2 = manager.getAccount("2");
		Account a3 = manager.getAccount("3");
		Budget b1 = manager.getBudget("1");
		Budget b2 = manager.getBudget("2");
	}

	/**
	 * A manager with 3 accounts (named 1 to 3) and 2 budgets (named 1 and 2).
	 * 
	 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
	 * 
	 */
	class Manager3by2 extends Manager {
		public Manager3by2() {
			Account a = new Account();
			a.setName("1");
			addAccount(a);

			a = new Account();
			a.setName("2");
			addAccount(a);

			a = new Account();
			a.setName("3");
			addAccount(a);

			Budget b = new Budget();
			b.setName("1");
			addBudget(b);

			b = new Budget();
			b.setName("2");
			addBudget(b);
		}
	}
}
