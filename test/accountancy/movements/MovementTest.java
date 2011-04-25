package accountancy.movements;

import java.math.BigDecimal;

import org.junit.Test;

import accountancy.accounts.Account;
import accountancy.budgets.Budget;
import accountancy.movements.Movement.Sense;
import static org.junit.Assert.*;

public class MovementTest {
    
    @Test
    public void lockTest() {
        Movement movement = new Movement();
        assertFalse(movement.isLocked());
        
        movement.setAccount(new Account());
        movement.setValue(new BigDecimal("100"));
        movement.assignValueToBudget(new Budget(), new BigDecimal("100"));
        
        movement.setLocked(true);
        assertTrue(movement.isLocked());
        try {
            movement.setAccount(new Account());
            fail("no exception thrown");
        }
        catch (LockedMovementException ex) {
        }
        try {
            movement.setValue(new BigDecimal("100"));
            fail("no exception thrown");
        }
        catch (LockedMovementException ex) {
        }
        try {
            movement.setSense(Sense.INPUT);
            fail("no exception thrown");
        }
        catch (LockedMovementException ex) {
        }
        try {
            movement.assignValueToBudget(new Budget(), new BigDecimal("100"));
            fail("no exception thrown");
        }
        catch (LockedMovementException ex) {
        }
        
        movement.setLocked(false);
        assertFalse(movement.isLocked());
        movement.setAccount(new Account());
        movement.setValue(new BigDecimal("500"));
        movement.assignValueToBudget(new Budget(), new BigDecimal("100"));
    }
    
    @Test
    public void inputOutputTest() {
        Movement movement = new Movement();
        assertFalse(movement.isInput());
        assertFalse(movement.isOutput());
        
        movement.setValue(new BigDecimal("100"));
        assertTrue(movement.isInput());
        assertFalse(movement.isOutput());
        
        movement.setValue(new BigDecimal("-100"));
        assertFalse(movement.isInput());
        assertTrue(movement.isOutput());
    }
    
    @Test
    public void valueTest() {
        Movement movement = new Movement();
        assertEquals(BigDecimal.ZERO, movement.getValue());
        
        String value = "200";
        movement.setValue(new BigDecimal(value));
        assertEquals(value, movement.getValue().toString());

        value = "-300.5664";
        movement.setValue(new BigDecimal(value));
        assertEquals(value, movement.getValue().toString());

    }
    
    @Test
    public void accountTest() {
        Movement movement = new Movement();
        
        Account account = new Account();
        movement.setAccount(account);
        assertEquals(account, movement.getAccount());
    }
    
    @Test
    public void budgetsTest() {
        Movement movement = new Movement();
        
        Account account = new Account();
        Budget b1 = new Budget();
        b1.setName("1");
        Budget b2 = new Budget();
        b2.setName("2");
        Budget b3 = new Budget();
        b3.setName("3");
        BigDecimal value = new BigDecimal("200");
        BigDecimal valueB1 = new BigDecimal("100");
        BigDecimal valueB2 = new BigDecimal("100");
        BigDecimal valueB3 = new BigDecimal("100");
        
        movement.setAccount(account);
        movement.setValue(value);
        movement.assignValueToBudget(b1, valueB1);
        movement.assignValueToBudget(b2, valueB1);
        assertArrayEquals(new Budget[] { b1, b2 },
                movement.getBudgetsAssigned());
        assertEquals(valueB1, movement.getValueForBudget(b1));
        assertEquals(valueB2, movement.getValueForBudget(b2));
        assertEquals(valueB1.add(valueB2), movement.getTotalValueAssigned());
        
        valueB2 = new BigDecimal("30");
        movement.assignValueToBudget(b2, valueB2);
        assertArrayEquals(new Budget[] { b1, b2 },
                movement.getBudgetsAssigned());
        assertEquals(valueB1, movement.getValueForBudget(b1));
        assertEquals(valueB2, movement.getValueForBudget(b2));
        assertEquals(valueB1.add(valueB2), movement.getTotalValueAssigned());
        
        movement.unassignBudget(b2);
        movement.assignValueToBudget(b3, valueB3);
        assertArrayEquals(new Budget[] { b1, b3 },
                movement.getBudgetsAssigned());
        assertEquals(valueB1, movement.getValueForBudget(b1));
        assertEquals(valueB3, movement.getValueForBudget(b3));
        assertEquals(valueB1.add(valueB3), movement.getTotalValueAssigned());
        
        try {
            movement.assignValueToBudget(b2, valueB2);
            fail("no exeption thrown");
        }
        catch (MovementExceededValueException ex) {
        }
    }
    
    @Test
    public void cloneTest() {
        Movement movement = new MovementExample();
        Movement clone = movement.clone();
        
        assertEquals(movement.getAccount(), clone.getAccount());
        assertEquals(movement.getSense(), clone.getSense());
        assertEquals(movement.getTotalValueAssigned(),
                clone.getTotalValueAssigned());
        assertEquals(movement.getValue(), clone.getValue());
        assertArrayEquals(movement.getBudgetsAssigned(),
                clone.getBudgetsAssigned());
        for (Budget budget : clone.getBudgetsAssigned()) {
            assertEquals(movement.getValueForBudget(budget),
                    clone.getValueForBudget(budget));
        }
    }
    
    class MovementExample extends Movement {
        public MovementExample() {
            setAccount(new Account());
            setSense(Sense.OUTPUT);
            setValue(new BigDecimal("300"));
            assignValueToBudget(new Budget(), new BigDecimal("20"));
            assignValueToBudget(new Budget(), new BigDecimal("30"));
            assignValueToBudget(new Budget(), new BigDecimal("10"));
            setLocked(true);
        }
    }
}
