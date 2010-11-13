package accountancy;

import java.math.BigDecimal;

/**
 * This is the parent class of accounts and budgets. It implements the common
 * fields, like name and value, with the associated methods.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class AccountancyElement {
	/**
	 * The name applied at the creation of the element.
	 */
	public static final String DEFAULT_NAME = "<unnamed>";
	/**
	 * The name of the element. At the creation of the element, it is
	 * initialized with {@link AccountancyElement#DEFAULT_NAME}.
	 */
	private String name = DEFAULT_NAME;
	/**
	 * The value (amount of money) of the element. At the creation of the
	 * element, it is initialized to zero ({@link BigDecimal#ZERO} ).
	 */
	private BigDecimal value = BigDecimal.ZERO;

	public BigDecimal getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * @exception NullPointerException if the argument is null
	 */
	public void setValue(BigDecimal newValue) {
		if (newValue == null) {
			throw new NullPointerException();
		}
		value = newValue;
	}

	/**
	 * @exception NullPointerException if the argument is null
	 */
	public void setName(String newName) {
		if (newName == null) {
			throw new NullPointerException();
		}
		name = newName;
	}

}
