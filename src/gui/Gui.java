package gui;

import accountancy.accounts.Account;
import accountancy.budgets.Budget;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.math.BigDecimal;
import java.util.Collection;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import manager.Manager;

/**
 *
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 */
public class Gui extends JFrame {

    private Manager manager;

    public static void main(String args[]) {
        new Gui().setVisible(true);
    }

    public Gui() {
        setTitle("Account Manager");

        initManager();

        initComponents();
        setSize(new Dimension(400, 600));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        LayoutManager layout = new GridLayout(1, 1);
        setLayout(layout);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Accounts", createAccountsTab());
        tabbedPane.add("Budgets", createBudgetsTab());
        tabbedPane.add("Movements", createMovementTab());
        layout.addLayoutComponent(null, tabbedPane);
        add(tabbedPane);
    }

    private JPanel createAccountsTab() {
        return createTableStuff(manager.getAccounts());
    }

    private JPanel createBudgetsTab() {
        return createTableStuff(manager.getBudgets());
    }

    private JPanel createMovementTab() {
        return createTableStuff(manager.getMovementsIDs());
    }

    private <T> JPanel createTableStuff(Collection<T> tableContent) {
        LayoutManager layout = new GridLayout(2, 3);
        JPanel container = new JPanel(layout);

        JTable table = new JTable();
        table.setAutoCreateColumnsFromModel(true);
        table.setModel(new TableModel<T>(tableContent));
        layout.addLayoutComponent(null, table);
        container.add(table);
        return container;
    }

    private void initManager() {
        //TODO replace this test case by a save reading
        manager = new Manager();

        Account account = new Account();
        account.setName("A1");
        account.setValue(new BigDecimal("100"));
        account.setLimit(new BigDecimal("1000"));
        manager.addAccount(account);

        account = new Account();
        account.setName("A2");
        account.setValue(new BigDecimal("200"));
        account.setLimit(new BigDecimal("2000"));
        manager.addAccount(account);

        account = new Account();
        account.setName("A3");
        account.setValue(new BigDecimal("300"));
        account.setLimit(new BigDecimal("3000"));
        manager.addAccount(account);

        Budget budget = new Budget();
        budget.setName("B1");
        budget.setValue(new BigDecimal("100"));
        manager.addBudget(budget);

        budget = new Budget();
        budget.setName("B2");
        budget.setValue(new BigDecimal("200"));
        manager.addBudget(budget);
    }
}
