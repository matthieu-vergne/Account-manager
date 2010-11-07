package accountancy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import accountancy.budgets.Budget;


public class AccountancyElementTest {

	@Test
	public void valueTest() {
		AccountancyElement element = new AccountancyElement();
		assertEquals(BigDecimal.ZERO, element.getValue());
		
		try {
			element.setValue(null);
			fail("no exception thrown");
		} catch (NullPointerException ex) {
		}
		
		for (String testString : new String[] { "1000", "0", "-150.32",
				"5598861335781995486.45" }) {
			BigDecimal testValue = new BigDecimal(testString);
			element.setValue(testValue);
			assertEquals(testValue, element.getValue());
			assertEquals(testString, element.getValue().toString());
		}
	}

	@Test
	public void nameTest() {
		AccountancyElement element = new AccountancyElement();
		assertEquals(Budget.DEFAULT_NAME, element.getName());
		
		try {
			element.setName(null);
			fail("no exception thrown");
		} catch (NullPointerException ex) {
		}
		
		String testName = "test";
		element.setName(testName);
		assertEquals(testName, element.getName());
	}

}
