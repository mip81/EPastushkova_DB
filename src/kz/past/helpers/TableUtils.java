package kz.past.helpers;

import java.util.HashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableUtils {
	/**
	 * Method make the proper width of table
	 * in the table of report
	 * @param table
	 */
	public static void setWidthForTableReport(JTable table){
		int colCount = table.getColumnCount();
		
		TableColumnModel tcm = table.getColumnModel();
		for(int i=1 ; i < colCount ; i++){
		//TODO write the cycle to change width for columns	
		}
		//tcm.getColumn(1).setPreferredWidth(preferredWidth);
	}
	
	
	/**
	 * Set preferable width for table Masters
	 * @param JTable tblMasters
	 */
	public static void setPrefWidthMasterTbl(JTable tblMasters){
		tblMasters.getColumnModel().getColumn(0).setPreferredWidth(3);
		tblMasters.getColumnModel().getColumn(1).setPreferredWidth(80);
		tblMasters.getColumnModel().getColumn(2).setPreferredWidth(80);
		tblMasters.getColumnModel().getColumn(3).setPreferredWidth(80);
		tblMasters.getColumnModel().getColumn(5).setPreferredWidth(90);
		tblMasters.getColumnModel().getColumn(6).setPreferredWidth(90);
		
	}
	/**
	 * Set cell edit masterid
	 * as combobox
	 * @param table
	 * @param tColumn
	 */
	public static void setComboBoxColumnMasterId(JTable table, TableColumn tColumn){
		HashMap<Integer, String> hmIdMaster = (HashMap<Integer, String>) DBFunctions.getMasters(Conn.getConnection(), false);
		JComboBox<String> comboBox = new JComboBox<String>();
			for (String master : hmIdMaster.values()){
				comboBox.addItem(master);
			}
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Выбор мастера");
		tColumn.setCellEditor(new DefaultCellEditor(comboBox));
		tColumn.setCellRenderer(renderer);
	}
	
	public static void setComboBoxColumnProcType(JTable table, TableColumn tColumn){
		HashMap<Integer, String> hmIdProcType = (HashMap<Integer, String>) DBFunctions.getProcType(Conn.getConnection(), false);
		JComboBox<String> comboBox = new JComboBox<String>();
			for (String procType : hmIdProcType.values()){
				comboBox.addItem(procType);
			}
			
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Выбор типа процедуры");
		tColumn.setCellEditor(new DefaultCellEditor(comboBox));
		tColumn.setCellRenderer(renderer);

	}
	
	public static void setComboBoxColumnProc(JTable table, TableColumn tColumn){
		HashMap<Integer, String> hmIdProc = (HashMap<Integer, String>) DBFunctions.getProc(Conn.getConnection(), false);
		JComboBox<String> comboBox = new JComboBox<String>();
			for (String proc : hmIdProc.values()){
				comboBox.addItem(proc);
			}
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Выбор процедуры");
		tColumn.setCellEditor(new DefaultCellEditor(comboBox));
		tColumn.setCellRenderer(renderer);

	}
}
