package gui;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.event.TableModelListener;

import accountancy.accounts.Account;
import accountancy.budgets.Budget;
import accountancy.movements.Movement;

/**
 *
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 */
public class TableModel<T> implements javax.swing.table.TableModel {

    private final Collection<T> content;
    private final Collection<TableModelListener> listeners;

    public TableModel() {
        this(new ArrayList<T>());
    }

    public TableModel(Collection<T> content) {
        this.content = new ArrayList<T>(content);
        this.listeners = new ArrayList<TableModelListener>();
    }

    public int getRowCount() {
        return content.size();
    }

    private String[] getColumns() {
        if (content.isEmpty()) {
            return new String[]{"empty"};
        } else {
            Object element = content.toArray()[0];
            if (element instanceof Account) {
                return new String[]{"Name", "Value", "Limit"};
            } else if (element instanceof Budget) {
                return new String[]{"Name", "Value"};
            } else if (element instanceof Movement) {
                return new String[]{"Value", "Account", "Sense"};
            } else {
                throw new IllegalStateException("Not supported class : " + element.
                        getClass());
            }
        }
    }

    public int getColumnCount() {
        return getColumns().length;
    }

    public String getColumnName(int columnIndex) {
        return getColumns()[columnIndex];
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object element = content.toArray()[rowIndex];
        if (element instanceof Account) {
            Account account = (Account) element;

            switch (columnIndex) {
                case 0:
                    return account.getName();
                case 1:
                    return account.getValue();
                case 2:
                    return account.getLimit();
                default:
                    return null;
            }
        } else if (element instanceof Budget) {
            Budget budget = (Budget) element;

            switch (columnIndex) {
                case 0:
                    return budget.getName();
                case 1:
                    return budget.getValue();
                default:
                    return null;
            }
        } else if (element instanceof Movement) {
            Movement movement = (Movement) element;

            switch (columnIndex) {
                case 0:
                    return movement.getValue();
                case 1:
                    return movement.getAccount();
                case 2:
                    return movement.getSense();
                default:
                    return null;
            }
        } else {
            throw new IllegalStateException("Not supported class : " + element.
                    getClass());
        }
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
}
