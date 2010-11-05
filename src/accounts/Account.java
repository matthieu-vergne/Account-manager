package accounts;

import java.math.BigDecimal;

public class Account {

	/**
	 * Name for unnamed accounts.
	 */
	public static final String DEFAULT_NAME = "<unnamed>";
	/**
	 * A high limit to simulate an infinite limit.<br/>
	 * <br/>
	 * <b>Be careful to the real limit (look the value of this constant).</b>
	 */
	public static final BigDecimal NO_LIMIT = new BigDecimal("10e+1000");
	private BigDecimal value = BigDecimal.ZERO;
	private String name = DEFAULT_NAME;
	private BigDecimal limit = NO_LIMIT;

	public BigDecimal getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setValue(BigDecimal newValue) {
		if (newValue.compareTo(limit) > 0) {
			throw new AccountLimitException(limit, newValue);
		}
		value = newValue;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setLimit(BigDecimal newLimit) {
		limit = newLimit;
	}

	public BigDecimal getLimit() {
		return limit;
	}

}
