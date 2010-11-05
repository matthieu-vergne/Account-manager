package accountancy;

import java.math.BigDecimal;

public class AccountancyElement {
	/**
	 * Name for unnamed budgets.
	 */
	public static final String DEFAULT_NAME = "<unnamed>";
	private String name = DEFAULT_NAME;
	private BigDecimal value = BigDecimal.ZERO;

	public BigDecimal getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setValue(BigDecimal newValue) {
		value = newValue;
	}

	public void setName(String newName) {
		name = newName;
	}

}
