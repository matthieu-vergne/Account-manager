package accountancy.accounts;

import java.math.BigDecimal;

import org.junit.Test;

import accountancy.accounts.Account;
import accountancy.accounts.AccountLimitException;
import static org.junit.Assert.*;

public class AccountTest {

	@Test
	public void limitTest() {
		Account account = new Account();
		assertEquals(Account.INFINITE_LIMIT, account.getLimit());
		
		try {
			account.setLimit(null);
			fail("no exception thrown");
		} catch (NullPointerException ex) {
		}
		
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
		
		account.setForcedValue(testValueNotApplied);
		assertEquals(testValueNotApplied, account.getValue());
	}
}
