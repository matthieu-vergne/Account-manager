package accountancy.movements;

import java.math.BigDecimal;

import org.junit.Test;
import static org.junit.Assert.*;

public class MovementTest {

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

		String accountName = "test";
		movement.setAccount(accountName);
		assertEquals(accountName, movement.getAccount());
	}

	@Test
	public void budgetsTest() {
		Movement movement = new Movement();

		String account = "test";
		String b1 = "1";
		String b2 = "2";
		String b3 = "3";
		BigDecimal value = new BigDecimal("200");
		BigDecimal valueB1 = new BigDecimal("100");
		BigDecimal valueB2 = new BigDecimal("100");
		BigDecimal valueB3 = new BigDecimal("100");

		movement.setAccount(account);
		movement.setValue(value);
		movement.assignValueToBudget(b1, valueB1);
		movement.assignValueToBudget(b2, valueB1);
		assertArrayEquals(new String[]{b1, b2}, movement.getBudgetsAssigned());
		assertEquals(valueB1, movement.getValueForBudget(b1));
		assertEquals(valueB2, movement.getValueForBudget(b2));
		assertEquals(valueB1.add(valueB2), movement.getTotalValueAssigned());

		try {
			movement.assignValueToBudget(b3, valueB1);
			fail("no exeption thrown");
		} catch (MovementExceededValueException ex) {
		}
	}
}
