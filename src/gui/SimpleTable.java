package gui;

import accountancy.AccountancyElement;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author <a href="matthieu.vergne@gmail.com">Matthieu Vergne</a>
 */
public class SimpleTable<T> extends JTable {

    private final LinkedHashMap<String, Method> columnDescriptions = new LinkedHashMap<String, Method>();

    public LinkedHashMap<String, Method> getColumnDescriptionMap() {
        return columnDescriptions;
    }

    public void refreshWith(Collection<T> elements) {
        LinkedHashMap<String, Method> colDescriptions = getColumnDescriptionMap();

        int columnCount = colDescriptions.size();
        Object[] columnNames = new Object[columnCount];
        Object[][] data = new Object[elements.size()][columnCount];
        int col = 0;
        for (Map.Entry<String, Method> entry : colDescriptions.entrySet()) {
            columnNames[col] = entry.getKey();

            Iterator<T> iterator = elements.iterator();
            for (int row = 0; row < data.length; row++) {
                T account = iterator.next();
                try {
                    data[row][col] = entry.getValue().invoke(account);
                } catch (Exception ex) {
                    Logger.getLogger(SimpleTable.class.getName()).
                            log(Level.SEVERE, null, ex);
                }
            }
            col++;
        }

        setModel(new DefaultTableModel(data, columnNames));
    }
}
