package kz.past.forms.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import kz.past.helpers.TableUtils;
import kz.past.helpers.tablemodels.MasterPricesTableModel;

public class OtherDirsSettingsPanel {
	
	/**
	 * get the panel with 
	 * settings table
	 * @return JPanel
	 */
	static public JPanel getPanel(){
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelMasterPrice = new JPanel(new BorderLayout());
			panelMasterPrice.setBorder(BorderFactory.createTitledBorder("Процент мастеров"));
			//panel.setPreferredSize(new Dimension(panel.getWidth(), 130));
		//JPanel panelProctype = new JPanel(new BorderLayout());
			//panelProctype.setBorder(BorderFactory.createTitledBorder("Типы процедуры"));
		MasterPricesTableModel mpt = new MasterPricesTableModel();
		JTable table = new JTable(mpt);
		
		//CHANGE EDITOR FOR SOME CELLS IN THE TABLE (COMBOBOX)
		JScrollPane scrlPane = new JScrollPane();
			TableUtils.setComboBoxColumnMasterId(table, table.getColumnModel().getColumn(1));
			TableUtils.setComboBoxColumnProcType(table, table.getColumnModel().getColumn(3));
			TableUtils.setComboBoxColumnProc(table, table.getColumnModel().getColumn(2));
			
		scrlPane.setViewportView(table);
		scrlPane.setEnabled(true);;
		panelMasterPrice.add(scrlPane, BorderLayout.NORTH);
		
		
		
		panel.add(panelMasterPrice, BorderLayout.NORTH);
		//panel.add(panelProctype, BorderLayout.CENTER);
		
		return panel;
	}

}
