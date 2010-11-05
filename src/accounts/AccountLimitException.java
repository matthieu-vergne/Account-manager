package accounts;

import java.math.BigDecimal;

@SuppressWarnings("serial")
public class AccountLimitException extends RuntimeException {

	public AccountLimitException() {
		super("the limit of the account is overflowed");
	}

	public AccountLimitException(BigDecimal limit) {
		super("the limit of the account (" + limit + ") is overflowed");
	}

	public AccountLimitException(BigDecimal limit, BigDecimal value) {
		super("the limit of the account (" + limit + ") is overflowed ("
				+ value + ")");
	}
}
