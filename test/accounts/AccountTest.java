package accounts;

import java.math.BigDecimal;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {

	@Test
	public void accountCreationTest() {
		Account account = new Account();
		assertEquals(BigDecimal.ZERO, account.getValue());
		assertEquals(Account.DEFAULT_NAME, account.getName());
		assertEquals(Account.NO_LIMIT, account.getLimit());
	}

	@Test
	public void valueTest() {
		Account account = new Account();
		for (String testString : new String[] { "1000", "0", "-150.32",
				"5598861335781995486.45" }) {
			BigDecimal testValue = new BigDecimal(testString);
			account.setValue(testValue);
			assertEquals(testValue, account.getValue());
			assertEquals(testString, account.getValue().toString());
		}
	}

	@Test
	public void nameTest() {
		Account account = new Account();
		String testName = "test";
		account.setName(testName);
		assertEquals(testName, account.getName());
	}

	@Test
	public void limitTest() {
		Account account = new Account();
		BigDecimal testLimit = new BigDecimal("1000");
		account.setLimit(testLimit);
		assertEquals(testLimit, account.getLimit());

		BigDecimal testValue = new BigDecimal("100");
		account.setValue(testValue);
		assertEquals(testValue, account.getValue());

		testValue = new BigDecimal("1000");
		account.setValue(testValue);
		assertEquals(testValue, account.getValue());

		BigDecimal testValueNotApplied = new BigDecimal("1001");
		try {
			account.setValue(testValueNotApplied);
			fail("no exception thrown");
		} catch (AccountLimitException ex) {
			assertEquals(testValue, account.getValue());
			assertTrue(ex.getMessage().contains(testLimit.toString()));
			assertTrue(ex.getMessage().contains(testValueNotApplied.toString()));
		}
	}
}
