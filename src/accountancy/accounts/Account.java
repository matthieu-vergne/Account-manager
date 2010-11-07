package accountancy.accounts;

import java.math.BigDecimal;

import accountancy.AccountancyElement;

/**
 * An account is a stock of money. It can have a limit, like a cap in a bank
 * account.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class Account extends AccountancyElement {

	/**
	 * A high limit to simulate an infinite limit.<br/>
	 * <br/>
	 * <b>Be careful to the real limit (look the value of this constant).</b>
	 */
	public static final BigDecimal INFINITE_LIMIT = new BigDecimal("10e+1000");
	/**
	 * The limit of this account. At the creation of the account, this limit is
	 * set to {@link Account#INFINITE_LIMIT}.
	 */
	private BigDecimal limit = INFINITE_LIMIT;

	@Override
	/*
	 * This method is overrided to consider the limit of the account.
	 */
	public void setValue(BigDecimal newValue) {
		setValue(newValue, true);
	}

	/**
	 * 
	 * @param newValue
	 *            the value to set
	 * @param checkLimit
	 *            tell if the limit must be consider
	 * @exception AccountLimitException
	 *                if the limit is considered and the given value is greater
	 *                than this limit
	 */
	private void setValue(BigDecimal newValue, boolean checkLimit) {
		if (checkLimit && newValue.compareTo(limit) > 0) {
			throw new AccountLimitException(limit, newValue);
		}
		super.setValue(newValue);
	}

	/**
	 * @exception NullPointerException
	 *                if the argument is null
	 */
	public void setLimit(BigDecimal newLimit) {
		if (newLimit == null) {
			throw new NullPointerException();
		}
		limit = newLimit;
	}

	public BigDecimal getLimit() {
		return limit;
	}

	/**
	 * Same as {@link Account#setValue(BigDecimal)}, but without considering the
	 * limit (to force the account to a value greater than the limit).
	 */
	public void setForcedValue(BigDecimal newValue) {
		setValue(newValue, false);
	}

}
