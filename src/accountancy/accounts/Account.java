package accountancy.accounts;

import java.math.BigDecimal;

import accountancy.AccountancyElement;

public class Account extends AccountancyElement {

	/**
	 * A high limit to simulate an infinite limit.<br/>
	 * <br/>
	 * <b>Be careful to the real limit (look the value of this constant).</b>
	 */
	public static final BigDecimal NO_LIMIT = new BigDecimal("10e+1000");
	private BigDecimal limit = NO_LIMIT;

	@Override
	public void setValue(BigDecimal newValue) {
		setValue(newValue, true);
	}

	private void setValue(BigDecimal newValue, boolean checkLimit) {
		if (checkLimit && newValue.compareTo(limit) > 0) {
			throw new AccountLimitException(limit, newValue);
		}
		super.setValue(newValue);
	}

	public void setLimit(BigDecimal newLimit) {
		limit = newLimit;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	public void setForcedValue(BigDecimal forcedValue) {
		setValue(forcedValue, false);
	}

}
