package accountancy.movements;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//TODO add this javadoc
public class Movement {

	/**
	 * The complete value of the movement. Values assigned to budgets are
	 * checked relatively to this one.
	 */
	private BigDecimal value = BigDecimal.ZERO;
	private String account;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public void setAccount(String accountName) {
		account = accountName;
	}

	public String getAccount() {
		return account;
	}

	private final Map<String, BigDecimal> assignments = new HashMap<String, BigDecimal>();

	public void assignValueToBudget(String budgetName, BigDecimal value) {
		if (getTotalValueAssigned().add(value).compareTo(getValue()) > 0) {
			throw new MovementExceededValueException();
		}
		assignments.put(budgetName, value);
	}

	public String[] getBudgetsAssigned() {
		String[] array = assignments.keySet().toArray(new String[] {});
		Arrays.sort(array);
		return array;
	}

	public BigDecimal getValueForBudget(String budgetName) {
		return assignments.get(budgetName);
	}

	public BigDecimal getTotalValueAssigned() {
		BigDecimal total = BigDecimal.ZERO;
		for (BigDecimal value : assignments.values()) {
			total = total.add(value);
		}
		return total;
	}

}
