package manager;

import org.junit.Test;


import accountancy.accounts.Account;
import accountancy.budgets.Budget;
import static org.junit.Assert.*;

public class ManagerTest {

	@Test
	public void managerCreationTest() {
		Manager manager = new Manager();
		assertEquals(0, manager.getAccounts().size());
		assertEquals(0, manager.getBudgets().size());
	}

	@Test
	public void accountsManagingTest() {
		Manager manager = new Manager();

		Account a1 = new Account();
		a1.setName("a1");
		Account a2 = new Account();
		a2.setName("a2");
		Account a3 = new Account();
		a3.setName("a3");
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
	}

	@Test
	public void budgetsManagingTest() {
		Manager manager = new Manager();

		Budget a1 = new Budget();
		a1.setName("a1");
		Budget a2 = new Budget();
		a2.setName("a2");
		Budget a3 = new Budget();
		a3.setName("a3");
		manager.addBudget(a2);
		manager.addBudget(a3);
		manager.addBudget(a1);
		assertArrayEquals(new Object[] { a1, a2, a3 }, manager.getBudgets()
				.toArray());

		try {
			manager.addBudget(a1);
			fail("no exception thrown");
		} catch (AlreadyExistingBudgetException ex) {
		}
	}
}
