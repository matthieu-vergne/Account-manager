package manager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import manager.Manager.Link;

import org.junit.Test;

import accountancy.accounts.Account;
import accountancy.budgets.Budget;

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
	public void linkTest() {
		Manager manager = new Manager3by2();
		Account a1 = manager.getAccount("1");
		Account a2 = manager.getAccount("2");
		Budget b1 = manager.getBudget("1");
		Budget b2 = manager.getBudget("2");

		Link l1 = manager.new Link(a1, b1);
		Link l2 = manager.new Link(a1, b1);
		Link l3 = manager.new Link(a1, b2);
		Link l4 = manager.new Link(a2, b1);
		Link l5 = manager.new Link(a2, b2);
		assertTrue(l1.equals(l1));
		assertTrue(l1.equals(l2));
		assertFalse(l1.equals(l3));
		assertFalse(l1.equals(l4));
		assertFalse(l1.equals(l5));
	}

	@Test
	public void linkingTest() {
		Manager manager = new Manager3by2();
		String a1 = "1";
		String a2 = "2";
		String a3 = "3";
		String b1 = "1";
		String b2 = "2";

		assertFalse(manager.isLinked(a1, b1));

		try {
			manager.getLinkValue(a1, b1);
			fail("no exception thrown");
		} catch (NoLinkException ex) {
		}
		
		BigDecimal linkValue = new BigDecimal("200");
		try {
			manager.changeLinkValue(a1, b1, linkValue);
			fail("no exception thrown");
		} catch (NoLinkException ex) {
		}
		
		manager.links(a1, b1);
		assertTrue(manager.isLinked(a1, b1));
		assertNull(manager.getLinkValue(a1, b1));

		try {
			manager.links(a1, b1);
			fail("no exception thrown");
		} catch (ExistingLinkException ex) {
		}

		manager.changeLinkValue(a1, b1, linkValue);
		assertTrue(manager.isLinked(a1, b1));
		assertEquals(linkValue, manager.getLinkValue(a1, b1));

		manager.links(a2, b1);
		manager.links(a2, b2);
		manager.links(a3, b2);
		assertArrayEquals(new String[]{a1, a2}, manager.getAccountsLinkedToBudget(b1));
		assertArrayEquals(new String[]{a2, a3}, manager.getAccountsLinkedToBudget(b2));
		assertArrayEquals(new String[]{b1}, manager.getBudgetsLinkedToAccount(a1));
		assertArrayEquals(new String[]{b1, b2}, manager.getBudgetsLinkedToAccount(a2));
		assertArrayEquals(new String[]{b2}, manager.getBudgetsLinkedToAccount(a3));
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
