package accountancy.movements;

import java.math.BigDecimal;

import org.junit.Test;

import accountancy.accounts.Account;
import accountancy.budgets.Budget;
import accountancy.movements.Movement.Sense;
import static org.junit.Assert.*;

public class MovementTest {

	@Test
	public void lockTest() {
		Movement movement = new Movement();
		assertFalse(movement.isLocked());

		movement.setAccount(new Account());
		movement.setValue(new BigDecimal("100"));
		movement.setSense(Sense.INPUT);
		movement.assignValueToBudget(new Budget(), new BigDecimal("100"));

		movement.setLocked(true);
		assertTrue(movement.isLocked());
		try {
			movement.setAccount(new Account());
			fail("no exeption thrown");
		} catch (LockedMovementException ex) {
		}
		try {
			movement.setValue(new BigDecimal("100"));
			fail("no exeption thrown");
		} catch (LockedMovementException ex) {
		}
		try {
			movement.setSense(Sense.INPUT);
			fail("no exeption thrown");
		} catch (LockedMovementException ex) {
		}
		try {
			movement.assignValueToBudget(new Budget(), new BigDecimal("100"));
			fail("no exeption thrown");
		} catch (LockedMovementException ex) {
		}
		
		movement.setLocked(false);
		assertFalse(movement.isLocked());
		movement.setAccount(new Account());
		movement.setValue(new BigDecimal("500"));
		movement.setSense(Sense.OUTPUT);
		movement.assignValueToBudget(new Budget(), new BigDecimal("100"));
	}

	@Test
	public void senseTest() {
		Movement movement = new Movement();
		assertNull(movement.getSense());

		movement.setSense(Sense.OUTPUT);
		assertEquals(Sense.OUTPUT, movement.getSense());

		movement.setSense(Sense.INPUT);
		assertEquals(Sense.INPUT, movement.getSense());
	}

	@Test
	public void valueTest() {
		Movement movement = new Movement();
		assertEquals(BigDecimal.ZERO, movement.getValue());

		BigDecimal value = new BigDecimal("200");
		movement.setValue(value);
		assertEquals(value, movement.getValue());
	}

	@Test
	public void accountTest() {
		Movement movement = new Movement();

		Account account = new Account();
		movement.setAccount(account);
		assertEquals(account, movement.getAccount());
	}

	@Test
	public void budgetsTest() {
		Movement movement = new Movement();

		Account account = new Account();
		Budget b1 = new Budget();
		b1.setName("1");
		Budget b2 = new Budget();
		b2.setName("2");
		Budget b3 = new Budget();
		b3.setName("3");
		BigDecimal value = new BigDecimal("200");
		BigDecimal valueB1 = new BigDecimal("100");
		BigDecimal valueB2 = new BigDecimal("100");
		BigDecimal valueB3 = new BigDecimal("100");

		movement.setAccount(account);
		movement.setValue(value);
		movement.assignValueToBudget(b1, valueB1);
		movement.assignValueToBudget(b2, valueB1);
		assertArrayEquals(new Budget[] { b1, b2 },
				movement.getBudgetsAssigned());
		assertEquals(valueB1, movement.getValueForBudget(b1));
		assertEquals(valueB2, movement.getValueForBudget(b2));
		assertEquals(valueB1.add(valueB2), movement.getTotalValueAssigned());

		valueB2 = new BigDecimal("30");
		movement.assignValueToBudget(b2, valueB2);
		assertArrayEquals(new Budget[] { b1, b2 },
				movement.getBudgetsAssigned());
		assertEquals(valueB1, movement.getValueForBudget(b1));
		assertEquals(valueB2, movement.getValueForBudget(b2));
		assertEquals(valueB1.add(valueB2), movement.getTotalValueAssigned());

		movement.unassignBudget(b2);
		movement.assignValueToBudget(b3, valueB3);
		assertArrayEquals(new Budget[] { b1, b3 },
				movement.getBudgetsAssigned());
		assertEquals(valueB1, movement.getValueForBudget(b1));
		assertEquals(valueB3, movement.getValueForBudget(b3));
		assertEquals(valueB1.add(valueB3), movement.getTotalValueAssigned());

		try {
			movement.assignValueToBudget(b2, valueB2);
			fail("no exeption thrown");
		} catch (MovementExceededValueException ex) {
		}
	}
}
