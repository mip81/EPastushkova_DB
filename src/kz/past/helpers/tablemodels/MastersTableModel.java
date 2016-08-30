package kz.past.helpers.tablemodels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.swing.table.AbstractTableModel;

import com.sun.rowset.CachedRowSetImpl;

import kz.past.helpers.AppSettings;

public class MastersTableModel extends AbstractTableModel{
	ArrayList alData = null;
	ArrayList alHeaders  = null;
	CachedRowSet crs = null; 
	
	
	public MastersTableModel() {
		System.out.println("Start MasterTableModel consrtuctor");
		try{
			
			alData = new ArrayList();
			alHeaders = new ArrayList();
			
			int[] key = {1}; //Key field in DB
			
			crs = new CachedRowSetImpl();
			crs.setUsername(AppSettings.getKeyDB("USERDB"));
			crs.setPassword(AppSettings.getKeyDB("PASSWORD"));
			crs.setUrl("jdbc:mysql://localhost/"+AppSettings.getKeyDB("DB")+"?relaxAutoCommit=true&useUnicode=true&"
					+ "characterEncoding=utf8");
			crs.setCommand("SELECT * FROM masters");
			crs.setKeyColumns(key);
			crs.execute();
			
			for(int i = 1; i < crs.getMetaData().getColumnCount()+1; i++){
				alHeaders.add( crs.getMetaData().getColumnName(i).toString() );
			}
			System.out.println( ">>> Generate table headers in MastersTableModel \n <<<< : "+alHeaders );
			while ( crs.next() ){
				List list = Arrays.asList( crs.getString(1), crs.getString(2), crs.getString(3),
					crs.getString(4),crs.getString(5),crs.getString(6),crs.getString(7),crs.getString(8),
					crs.getString(9),crs.getString(10),crs.getString(11),crs.getString("location") );
				//System.out.println(" "+list);
				
				alData.add( new ArrayList(list) );
			}
		}catch (Exception e) {
			System.out.println( "Err : MastersTableModel "+e.getClass()+" "+e.getMessage() );
		}
		
		
	}
	
	public void addNewRow(HashMap newRow){
		
		
	}
	
	public void updateRow(int id){
		
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		System.out.println(">>> Setting DATA into DB and keep it in the table");
		 ((ArrayList<String>)alData.get(rowIndex)).set(columnIndex, (String) aValue);
		 fireTableDataChanged();
	};
	
	@Override
	public int getColumnCount() {
		return alHeaders.size();
	}
	
	@Override
	public int getRowCount() {
		synchronized ( alData ) {
			return alData.size();
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return (String) alHeaders.get(column);
	}
	
	
	
	public void addTableModelListener(javax.swing.event.TableModelListener l) {};
	
	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {
		synchronized (alData) {
			return ( (ArrayList)(alData.get(rowIndex)) ).get(columnIndex);
		}
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	};
	public CachedRowSet getCashRowSet(){
		return crs;
	}
	
}
