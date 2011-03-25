
/*
 * Gui.java
 *
 * Created on 15 déc. 2010, 07:07:03
 */
package gui;

import accountancy.accounts.Account;
import accountancy.budgets.Budget;
import accountancy.movements.Movement;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import manager.Manager;

/**
 *
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 */
public class Gui extends javax.swing.JFrame {

    private final Manager manager;

    /** Creates new form Gui */
    public Gui() {
        initComponents();
        initAccountsTable();
        initBudgetsTable();
        initMovementsTable();

        //TODO récupérer depuis la sauvegarde si présente
        manager = new Manager();
        //TODO debug
        Account account = new Account();
        account.setName("account 1");
        account.setValue(new BigDecimal("100"));
        account.setLimit(new BigDecimal("100"));
        manager.addAccount(account);
        account = new Account();
        account.setName("account 2");
        account.setValue(new BigDecimal("100"));
        account.setLimit(new BigDecimal("200"));
        manager.addAccount(account);
        account = new Account();
        account.setName("account 3");
        account.setValue(new BigDecimal("300"));
        account.setLimit(new BigDecimal("500"));
        manager.addAccount(account);
        Budget budget = new Budget();
        budget.setName("budget 1");
        budget.setValue(new BigDecimal("50"));
        manager.addBudget(budget);
        budget = new Budget();
        budget.setName("budget 2");
        budget.setValue(new BigDecimal("200"));
        manager.addBudget(budget);
        manager.link("account 1", "budget 1");
        manager.link("account 2", "budget 1");
        manager.link("account 2", "budget 2");
        manager.link("account 3", "budget 2");
        Movement movement = new Movement();
        movement.setAccount(manager.getAccount("account 2"));
        movement.setValue(new BigDecimal("10"));
        movement.setSense(Movement.Sense.OUTPUT);
        BigDecimal movementID = manager.addMovement(movement);
        manager.applyMovement(movementID);
        movement = new Movement();
        movement.setAccount(manager.getAccount("account 2"));
        movement.setValue(new BigDecimal("20"));
        movement.setSense(Movement.Sense.INPUT);
        movementID = manager.addMovement(movement);
        manager.applyMovement(movementID);
        movement = new Movement();
        movement.setAccount(manager.getAccount("account 3"));
        movement.setValue(new BigDecimal("100"));
        movement.setSense(Movement.Sense.INPUT);
        manager.addMovement(movement); // not applied
        //TODO fin debug

        refreshAllTables();
    }

    private void initAccountsTable() throws RuntimeException {
        LinkedHashMap<String, String> fieldDescriptions =
                                      new LinkedHashMap<String, String>();
        fieldDescriptions.put("Name", "getName");
        fieldDescriptions.put("Value", "getValue");
        fieldDescriptions.put("Limit", "getLimit");
        initTable(accountsTable, Account.class, fieldDescriptions);
    }

    private void initBudgetsTable() throws RuntimeException {
        LinkedHashMap<String, String> fieldDescriptions =
                                      new LinkedHashMap<String, String>();
        fieldDescriptions.put("Name", "getName");
        fieldDescriptions.put("Value", "getValue");
        initTable(budgetsTable, Budget.class, fieldDescriptions);
    }

    private void initMovementsTable() throws RuntimeException {
        LinkedHashMap<String, String> fieldDescriptions =
                                      new LinkedHashMap<String, String>();
        fieldDescriptions.put("Account", "getAccount");
        fieldDescriptions.put("Value", "getValue");
        initTable(movementsTable, Movement.class, fieldDescriptions);
    }

    private void initTable(SimpleTable accountancyTable,
                           Class<?> accountancyClass,
                           LinkedHashMap<String, String> fieldDescriptions)
            throws RuntimeException {
        LinkedHashMap<String, Method> descriptionMap =
                                      accountancyTable.getColumnDescriptionMap();
        Class<?> classDescription;
        try {
            classDescription =
            ClassLoader.getSystemClassLoader().
                    loadClass(accountancyClass.getName());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        try {
            for (Entry<String, String> entry : fieldDescriptions.entrySet()) {
                descriptionMap.put(entry.getKey(), classDescription.getMethod(entry.getValue()));
            }
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (SecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

    public final void refreshAllTables() {
        refreshAccountsTable();
        refreshBudgetsTable();
        refreshMovementsTable();
    }

    public void refreshAccountsTable() {
        accountsTable.refreshWith(manager.getAccounts());
    }

    public void refreshBudgetsTable() {
        budgetsTable.refreshWith(manager.getBudgets());
    }

    public void refreshMovementsTable() {
        movementsTable.refreshWith(manager.getMovements());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        accountsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        accountsTable = new gui.SimpleTable<Account>();
        budgetsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        budgetsTable = new gui.SimpleTable<Budget>();
        movementsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        movementsTable = new gui.SimpleTable<Movement>();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        accountsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(accountsTable);

        javax.swing.GroupLayout accountsPanelLayout = new javax.swing.GroupLayout(accountsPanel);
        accountsPanel.setLayout(accountsPanelLayout);
        accountsPanelLayout.setHorizontalGroup(
            accountsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addContainerGap())
        );
        accountsPanelLayout.setVerticalGroup(
            accountsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accountsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPane.addTab("Accounts", accountsPanel);

        budgetsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(budgetsTable);

        javax.swing.GroupLayout budgetsPanelLayout = new javax.swing.GroupLayout(budgetsPanel);
        budgetsPanel.setLayout(budgetsPanelLayout);
        budgetsPanelLayout.setHorizontalGroup(
            budgetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(budgetsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addContainerGap())
        );
        budgetsPanelLayout.setVerticalGroup(
            budgetsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(budgetsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPane.addTab("Budgets", budgetsPanel);

        movementsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(movementsTable);

        javax.swing.GroupLayout movementsPanelLayout = new javax.swing.GroupLayout(movementsPanel);
        movementsPanel.setLayout(movementsPanelLayout);
        movementsPanelLayout.setHorizontalGroup(
            movementsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movementsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addContainerGap())
        );
        movementsPanelLayout.setVerticalGroup(
            movementsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movementsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPane.addTab("Movements", movementsPanel);

        getContentPane().add(tabbedPane);

        jMenu1.setText("File");
        menuBar.add(jMenu1);

        jMenu2.setText("Edit");
        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Gui().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel accountsPanel;
    private gui.SimpleTable accountsTable;
    private javax.swing.JPanel budgetsPanel;
    private gui.SimpleTable budgetsTable;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel movementsPanel;
    private gui.SimpleTable movementsTable;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables
}
