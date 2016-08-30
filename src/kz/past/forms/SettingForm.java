package kz.past.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.sun.prism.impl.ps.CachingShapeRep;
import com.sun.rowset.CachedRowSetImpl;

import kz.past.forms.panels.MastersPanel;
import kz.past.forms.panels.OtherDirsSettingsPanel;
import kz.past.forms.panels.TestPanel;
import kz.past.helpers.DBFunctions;
import kz.past.helpers.MyUtils;
import kz.past.helpers.tablemodels.MastersTableModel;

public class SettingForm extends JFrame {
		JPanel panel = new JPanel();
		JTable tblMasters = new JTable();
		JTabbedPane tabPane = new JTabbedPane();
		final String UPDATE = "UPDATE";
		final String NEW = "NEW";
	
		
		//Поля для формы мастеров
		MastersPanel mastersPanel = new MastersPanel();		
		OtherDirsSettingsPanel otherSetPanel = new OtherDirsSettingsPanel();
			
		//Поля другие справочники
		JPanel panelAddInfo = new JPanel();
	
		
public SettingForm() {		
		setSize(930,310);
		setTitle("Настройки программы");
		setLocationRelativeTo(null);
				
		tabPane.add("Справочник мастеров", mastersPanel.getPanel());
		tabPane.add("Другие справочники", otherSetPanel.getPanel());
		
		setContentPane(tabPane);

		
//////////////////////////
///TAB OTHER DIRECTORIES//
//////////////////////////
		
		panelAddInfo = OtherDirsSettingsPanel.getPanel();		
		setVisible(true);
	}

	
	
	


	
}
