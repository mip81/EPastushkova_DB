package kz.past.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;
import com.sun.javafx.event.EventQueue;

import kz.past.helpers.ParserCSV;

public class ActionImportCSV implements ActionListener{
	JFileChooser fileCh = new JFileChooser();
	FileNameExtensionFilter filterCSV = new FileNameExtensionFilter("CSV", "csv");
	DefaultTableModel dtm = null;
	JTable tbl = new JTable();
	Vector vNamesOfHeader = new Vector<String>();
	Vector vData = new Vector<String>();
	
	/**
	 * 
	 * @param tbl report's table
	 * @param dtm datamodel for table
	 * @param  Vector array of header's name
	 */
	public ActionImportCSV(JTable tblReport, DefaultTableModel dtm, String[] namesColumn ) {
		this.tbl = tblReport;
		this.dtm = dtm;
		vNamesOfHeader.addAll( Arrays.asList(namesColumn) );
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
				fileCh.setFileFilter(filterCSV); 
				int res = fileCh.showOpenDialog(null);
				
				if ( res == JFileChooser.APPROVE_OPTION ){
						System.out.println("Choosen file is "+fileCh.getSelectedFile());
					 ParserCSV pCSV = new ParserCSV(fileCh.getSelectedFile());
					 vData = pCSV.getvTableData(); // import data from csv file
					 dtm.setDataVector(vData, vNamesOfHeader);
					 //tbl.setModel(dtm);
					 System.out.println("DataModel ImportCSV "+tbl.getModel());
				}	
				System.out.println("Import CSV file into table.");
	}

}
