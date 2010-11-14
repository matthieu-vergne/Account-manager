package accountancy.movements;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import accountancy.accounts.Account;
import accountancy.budgets.Budget;

/**
 * A movement represent a transfer of money assigned to an account and,
 * relatively to this account, possibly some budgets. A movement can be an input
 * (increase the value of the element) or an output (decrease the value of the
 * element).
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class Movement implements Cloneable {

	/**
	 * The complete value of the movement. Values assigned to budgets are
	 * checked relatively to this one.
	 */
	private BigDecimal value = BigDecimal.ZERO;
	/**
	 * The account to apply the movement on.
	 */
	private Account account;
	/**
	 * The list of budgets to apply the movement on.
	 */
	private final Map<Budget, BigDecimal> assignments = new HashMap<Budget, BigDecimal>();

	/**
	 * Tell if this movement is locked. A locked movement implies no possible
	 * modifications of this movement.
	 */
	private boolean locked = false;

	/**
	 * The sense of the movement.
	 * 
	 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
	 * 
	 */
	public enum Sense {
		/**
		 * Add the money to the account/budgets.
		 */
		INPUT,
		/**
		 * Remove the money from the account/budgets.
		 */
		OUTPUT
	};

	/**
	 * The sense of the movement. By default it is an input.
	 */
	private Sense sense = Sense.INPUT;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		checkLock();
		// TODO check if there is a need to control value sign
		// basically a negative value should be forbidden, as a negative value
		// is equivalent to a positive value in the other sense
		this.value = value;
	}

	public void setAccount(Account account) {
		checkLock();
		this.account = account;
	}

	/**
	 * Check if the movement is locked. If it is, an exception is generated.
	 * 
	 * @exception LockedMovementException
	 *                if the movement is locked
	 */
	private void checkLock() {
		if (isLocked()) {
			throw new LockedMovementException();
		}
	}

	public Account getAccount() {
		return account;
	}

	/**
	 * Assign a part (possibly all) of this movement to a specific budget.
	 * 
	 * @param budget
	 *            the budget to apply the value to
	 * @param value
	 *            the amount of money to apply to the budget
	 */
	public void assignValueToBudget(Budget budget, BigDecimal value) {
		checkLock();
		BigDecimal ancientValue = assignments.remove(budget);
		if (getTotalValueAssigned().add(value).compareTo(getValue()) > 0) {
			assignments.put(budget, ancientValue);
			throw new MovementExceededValueException();
		}
		assignments.put(budget, value);
	}

	/**
	 * 
	 * @return the list of the budgets assigned in this movement
	 */
	public Budget[] getBudgetsAssigned() {
		Budget[] array = assignments.keySet().toArray(new Budget[] {});
		Arrays.sort(array, new Comparator<Budget>() {

			@Override
			public int compare(Budget b1, Budget b2) {
				return b1.getName().compareTo(b2.getName());
			}

		});
		return array;
	}

	/**
	 * Give the value assigned to the given budget.
	 * 
	 * @param budget
	 * @return
	 */
	public BigDecimal getValueForBudget(Budget budget) {
		return assignments.get(budget);
	}

	/**
	 * 
	 * @return the total value of all the budgets assigned
	 */
	public BigDecimal getTotalValueAssigned() {
		BigDecimal total = BigDecimal.ZERO;
		for (BigDecimal value : assignments.values()) {
			total = total.add(value);
		}
		return total;
	}

	/**
	 * Remove the given budget from assigned budgets if it is.
	 * 
	 * @param budget
	 *            the budget to remove
	 */
	public void unassignBudget(Budget budget) {
		checkLock();
		assignments.remove(budget);
	}

	public void setSense(Sense sense) {
        if (sense == null) {
            throw new NullPointerException("the sense cannot be null");
        }
		checkLock();
		this.sense = sense;
	}

	public Sense getSense() {
		return sense;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}

	@Override
	public Movement clone() {
	    Movement clone = new Movement();
	    clone.setAccount(getAccount());
	    clone.setValue(getValue());
	    for (Budget budget : assignments.keySet()) {
            clone.assignValueToBudget(budget, assignments.get(budget));
        }
	    return clone;
	}
}
