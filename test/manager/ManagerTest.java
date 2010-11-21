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
import accountancy.movements.Movement;
import accountancy.movements.Movement.Sense;
import java.io.File;

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
        assertArrayEquals(new Object[]{a1, a2, a3}, manager.getAccounts().
                toArray());

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
        assertArrayEquals(new Object[]{b1, b2, b3},
                manager.getBudgets().toArray());

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

        assertTrue(l2.equals(l1));
        assertTrue(l2.equals(l2));
        assertFalse(l2.equals(l3));
        assertFalse(l2.equals(l4));
        assertFalse(l2.equals(l5));

        assertFalse(l3.equals(l1));
        assertFalse(l3.equals(l2));
        assertTrue(l3.equals(l3));
        assertFalse(l3.equals(l4));
        assertFalse(l3.equals(l5));

        assertFalse(l4.equals(l1));
        assertFalse(l4.equals(l2));
        assertFalse(l4.equals(l3));
        assertTrue(l4.equals(l4));
        assertFalse(l4.equals(l5));

        assertFalse(l5.equals(l1));
        assertFalse(l5.equals(l2));
        assertFalse(l5.equals(l3));
        assertFalse(l5.equals(l4));
        assertTrue(l5.equals(l5));
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

        try {
            manager.unlink(a1, b1);
            fail("no exception thrown");
        } catch (NoLinkException ex) {
        }

        BigDecimal linkValue = new BigDecimal("200");
        try {
            manager.changeLinkValue(a1, b1, linkValue);
            fail("no exception thrown");
        } catch (NoLinkException ex) {
        }

        manager.link(a1, b1);
        assertTrue(manager.isLinked(a1, b1));
        assertNull(manager.getLinkValue(a1, b1));

        try {
            manager.link(a1, b1);
            fail("no exception thrown");
        } catch (ExistingLinkException ex) {
        }

        manager.unlink(a1, b1);
        manager.link(a1, b1, linkValue);
        assertTrue(manager.isLinked(a1, b1));
        assertEquals(linkValue, manager.getLinkValue(a1, b1));

        linkValue = linkValue.add(BigDecimal.ONE);
        manager.changeLinkValue(a1, b1, linkValue);
        assertTrue(manager.isLinked(a1, b1));
        assertEquals(linkValue, manager.getLinkValue(a1, b1));

        manager.link(a2, b1);
        manager.link(a2, b2);
        manager.link(a3, b2);
        assertArrayEquals(new String[]{a1, a2},
                manager.getAccountsLinkedToBudget(b1));
        assertArrayEquals(new String[]{a2, a3},
                manager.getAccountsLinkedToBudget(b2));
        assertArrayEquals(new String[]{b1},
                manager.getBudgetsLinkedToAccount(a1));
        assertArrayEquals(new String[]{b1, b2},
                manager.getBudgetsLinkedToAccount(a2));
        assertArrayEquals(new String[]{b2},
                manager.getBudgetsLinkedToAccount(a3));
    }

    @Test
    public void movementsTest() {
        //TODO add a test to see if the complete list of movements can be gotten
        Manager manager = new Manager3by2();
        Account a1 = manager.getAccount("1");
        Account a2 = manager.getAccount("2");
        Account a3 = manager.getAccount("3");
        Budget b1 = manager.getBudget("1");
        Budget b2 = manager.getBudget("2");

        manager.link(a1.getName(), b1.getName());
        manager.link(a2.getName(), b1.getName());
        manager.link(a2.getName(), b2.getName());
        manager.link(a3.getName(), b2.getName());

        BigDecimal valueA1 = new BigDecimal("100");
        BigDecimal valueA2 = new BigDecimal("100");
        BigDecimal valueA3 = new BigDecimal("100");
        BigDecimal valueB1 = new BigDecimal("150");
        BigDecimal valueB2 = new BigDecimal("150");

        a1.setValue(valueA1);
        a2.setValue(valueA2);
        a3.setValue(valueA3);
        b1.setValue(valueB1);
        b2.setValue(valueB2);

        try {
            manager.getMovement(new BigDecimal("100"));
            fail("no exception thrown");
        } catch (UnknownMovementException e) {
        }

        Movement movement = new Movement();
        BigDecimal Id = manager.addMovement(movement);
        assertEquals(movement, manager.getMovement(Id));

        try {
            manager.applyMovement(Id);
            fail("no exception thrown");
        } catch (InvalidMovementException e) {
        }

        movement.setAccount(a1);
        BigDecimal valueMovement = new BigDecimal("100");
        movement.setValue(valueMovement);
        movement.setSense(Sense.INPUT);
        assertFalse(manager.isApplied(Id));
        manager.applyMovement(Id);
        assertTrue(manager.isApplied(Id));
        assertEquals(valueA1.add(valueMovement), a1.getValue());
        assertEquals(valueA2, a2.getValue());
        assertEquals(valueA3, a3.getValue());
        assertEquals(valueB1, b1.getValue());
        assertEquals(valueB2, b2.getValue());

        try {
            manager.applyMovement(Id);
            fail("no exception thrown");
        } catch (AlreadyAppliedMovementException e) {
        }

        assertTrue(manager.isApplied(Id));
        manager.cancelMovement(Id);
        assertFalse(manager.isApplied(Id));
        assertEquals(valueA1, a1.getValue());
        assertEquals(valueA2, a2.getValue());
        assertEquals(valueA3, a3.getValue());
        assertEquals(valueB1, b1.getValue());
        assertEquals(valueB2, b2.getValue());

        try {
            manager.cancelMovement(Id);
            fail("no exception thrown");
        } catch (NotAppliedMovementException e) {
        }

        manager.removeMovement(Id);
        try {
            manager.getMovement(Id);
            fail("no exception thrown");
        } catch (UnknownMovementException e) {
        }

        BigDecimal[] movementsIds = new BigDecimal[]{
            manager.addMovement(new Movement()),
            manager.addMovement(new Movement()),
            manager.addMovement(new Movement()),
            manager.addMovement(new Movement()),
            manager.addMovement(new Movement()),};
        assertArrayEquals(movementsIds, manager.getMovementsIDs());
    }

    @Test
    public void savingTest() {
        /*
         * INITIALISATION
         */

        Manager manager = new Manager();

        Account a1 = new Account();
        Account a2 = new Account();
        Account a3 = new Account();
        Budget b1 = new Budget();
        Budget b2 = new Budget();

        a1.setName("1");
        a2.setName("2");
        a3.setName("3");
        b1.setName("1");
        b2.setName("2");

        a1.setValue(new BigDecimal("200"));
        a2.setValue(new BigDecimal("200"));
        a3.setValue(new BigDecimal("200"));
        b1.setValue(new BigDecimal("100"));
        b2.setValue(new BigDecimal("100"));

        manager.addAccount(a1);
        manager.addAccount(a2);
        manager.addAccount(a3);
        manager.addBudget(b1);
        manager.addBudget(b2);

        BigDecimal linkValue1 = new BigDecimal("10");
        BigDecimal linkValue2 = new BigDecimal("10");

        manager.link(a1.getName(), b1.getName());
        manager.link(a2.getName(), b1.getName(), linkValue1);
        manager.link(a2.getName(), b2.getName(), linkValue2);
        manager.link(a3.getName(), b2.getName());

        Movement m1 = new Movement();
        Movement m2 = new Movement();
        Movement m3 = new Movement();

        m1.setAccount(a1);
        m2.setAccount(a2);

        m1.setValue(new BigDecimal("10"));
        m2.setValue(new BigDecimal("20"));

        m1.setSense(Sense.INPUT);
        m2.setSense(Sense.OUTPUT);

        m2.assignValueToBudget(b1, new BigDecimal("10"));
        m2.assignValueToBudget(b2, new BigDecimal("10"));

        BigDecimal idM1 = manager.addMovement(m1);
        BigDecimal idM2 = manager.addMovement(m2);
        manager.addMovement(m3);

        manager.applyMovement(idM1);
        manager.applyMovement(idM2);

        /*
         * PERSISTENCE
         */

        String path = "persistenceTest.sav";
        String password = "pass";
        manager.save(path, password);
        Manager manager2 = Manager.getSaved(path, password);

        /*
         * TEST
         */

        assertArrayEquals(manager.getAccountNames(), manager2.getAccountNames());
        assertArrayEquals(manager.getBudgetNames(), manager2.getBudgetNames());
        assertArrayEquals(manager.getMovementsIDs(), manager2.getMovementsIDs());

        for (Account account : manager.getAccounts()) {
            String accountName = account.getName();
            final String[] list1 =
                           manager.getBudgetsLinkedToAccount(accountName);
            final String[] list2 = manager2.getBudgetsLinkedToAccount(
                    accountName);
            assertArrayEquals(list1, list2);
            for (String budgetName : list1) {
                assertEquals(manager.getLinkValue(accountName, budgetName), manager2.
                        getLinkValue(accountName, budgetName));
            }
        }

        for (Budget budget : manager.getBudgets()) {
            String budgetName = budget.getName();
            final String[] list1 =
                           manager.getAccountsLinkedToBudget(budgetName);
            final String[] list2 = manager2.getAccountsLinkedToBudget(
                    budgetName);
            assertArrayEquals(list1, list2);
            for (String accountName : list1) {
                assertEquals(manager.getLinkValue(accountName, budgetName), manager2.
                        getLinkValue(accountName, budgetName));
            }
        }

        for (BigDecimal id : manager.getMovementsIDs()) {
            final Movement movement1 = manager.getMovement(id);
            final Movement movement2 = manager2.getMovement(id);

            Account account1 = movement1.getAccount();
            Account account2 = movement2.getAccount();

            if (account1 == null || account2 == null) {
                assertNull(account1);
                assertNull(account2);
            } else {
                assertEquals(account1.getName(), account2.getName());
            }
            assertEquals(movement1.getSense(), movement2.getSense());
            assertEquals(movement1.getValue(), movement2.getValue());
            assertArrayEquals(movement1.getNamesOfBudgetsAssigned(), movement2.
                    getNamesOfBudgetsAssigned());
            for (Budget budget : movement1.getBudgetsAssigned()) {
                assertEquals(movement1.getValueForBudget(budget), movement2.
                        getValueForBudget(budget));
            }
        }
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
