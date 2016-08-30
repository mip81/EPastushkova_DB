package kz.past.helpers;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class MyUtils {
	public  static void setColumnsWidth(JTable table) {
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    JTableHeader th = table.getTableHeader();
	    for (int i = 0; i < table.getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        int prefWidth = 
	            Math.round(
	                (float) th.getFontMetrics(
	                    th.getFont()).getStringBounds(th.getTable().getColumnName(i),
	                    th.getGraphics()
	                ).getWidth()
	            );
	        column.setPreferredWidth(prefWidth + 10);
	    }
	}

}
