package kz.past.helpers.tablemodels;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;
import javax.swing.table.AbstractTableModel;

import com.sun.rowset.CachedRowSetImpl;

import kz.past.helpers.AppSettings;
import kz.past.helpers.Conn;
import kz.past.helpers.DBFunctions;

public class MasterPricesTableModel extends AbstractTableModel{
	private ArrayList alData = null;
	private ArrayList alHeaders  = null;
	private CachedRowSet crs = null;
	private HashMap<Integer, String> hmIdMasters = (HashMap<Integer, String>) DBFunctions.getMasters(Conn.getConnection(), false); 
	private HashMap<String, Integer> hmMastersId = (HashMap<String , Integer>) DBFunctions.getMasters(Conn.getConnection(), true);
	private HashMap<String, Integer> hmProcTypeId = (HashMap<String , Integer>) DBFunctions.getProcType(Conn.getConnection(), true);
	private HashMap<String, Integer> hmProc = (HashMap<String , Integer>) DBFunctions.getProc(Conn.getConnection(), true);
	
	private String currentLabelOfColumn = null;

	public MasterPricesTableModel() {
		System.out.println("Start MasterPricesTableModel consrtuctor");
		 
		
		try{
			alData  = new ArrayList();
			alHeaders = new ArrayList();
			
			int[] key = {1}; //Key field in DB
			
			crs = new CachedRowSetImpl();
			crs.setUsername(AppSettings.getKeyDB("USERDB"));
			crs.setPassword(AppSettings.getKeyDB("PASSWORD"));
			crs.setUrl("jdbc:mysql://localhost/"+AppSettings.getKeyDB("DB")+"?relaxAutoCommit=true&useUnicode=true&"
					+ "characterEncoding=utf8");
			crs.setCommand("SELECT * FROM masterprice");
			crs.setKeyColumns(key);
			crs.execute();
			
			//Getting headers of table
			for(int i = 1; i < crs.getMetaData().getColumnCount()+1; i++){
				alHeaders.add( crs.getMetaData().getColumnName(i).toString() );
			}
			System.out.println(" >>> Generate headers of table MASTERPRICE ");
			System.out.println(" <<< : "+alHeaders);
			
			// Getting data from DB
			while(crs.next()){
				 ArrayList<String> alD = new ArrayList<String>();
				for(int i=1 ; i<alHeaders.size()+1 ; ++i){
					alD.add(crs.getString(i));
					//System.out.println(" AL "+alD.toString());
				}
				alData.add(alD);	
			}

		}catch (Exception e) {
			System.out.println( "Err : MasterPricesTableModel "+e.getClass()+" "+e.getMessage() );
		}
	}
	
	public void addNewRow(HashMap newRow){
		
		
	}
	
	public void updateRow(int id){
		
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		 // Synchronize the block because ArrayList is not synchronized type
		synchronized (alData) {
			
			System.out.println(">>> : Change the data in the cell row: "+rowIndex+" column: "+columnIndex);
			
			if( getColumnName(columnIndex).equalsIgnoreCase("idmaster") ){
				Integer idMaster = hmMastersId.get(aValue);
						System.out.println(">>> Change master ID: "+idMaster+ " Last name: "+aValue);
				((ArrayList)alData.get(rowIndex)).set(columnIndex, idMaster);
				DBFunctions.updateField(crs, rowIndex, columnIndex, idMaster, 1);
			}
			
			if( getColumnName(columnIndex).equalsIgnoreCase("idproctype") ){
				System.out.println("");
				Integer idProcType = hmProcTypeId.get(aValue);
						System.out.println(">>> Change proctype ID: "+idProcType+ " Proctype: "+aValue);
				((ArrayList)alData.get(rowIndex)).set(columnIndex, idProcType);
				
				DBFunctions.updateField(crs, rowIndex, columnIndex, idProcType, 1);
			} 
			
			if( getColumnName(columnIndex).equalsIgnoreCase("idproc") ){
				System.out.println("");
				Integer idProc = hmProc.get(aValue);
						System.out.println(">>> Change proc ID: "+idProc+ " Proc: "+aValue);
				((ArrayList)alData.get(rowIndex)).set(columnIndex, idProc);
				
				DBFunctions.updateField(crs, rowIndex, columnIndex, idProc, 1);
			} 

		
			
			
		}
		
		 fireTableDataChanged();
	}
	
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
		synchronized ( alHeaders ) {
			return (String) alHeaders.get(column);
		}
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {
		
			synchronized (alData) {
				Object dataCell = ( (ArrayList)(alData.get(rowIndex)) ).get(columnIndex);
				try{
				    currentLabelOfColumn = new String(crs.getMetaData().getColumnLabel(columnIndex+1));
				}catch(Exception e){
					System.out.println(" ---- Error occured when get the name of column : \n >>>:  "+e.getMessage());
				}
			 
//			if(currentLabelOfColumn.equalsIgnoreCase("idmaster")){
//				return hmMasters.get(Integer.parseInt(dataCell.toString())).toUpperCase();
//			}
			return dataCell;
		}
	}
	
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) return false;
		return true;
	};
	public CachedRowSet getCashRowSet(){
		return crs;
	}
	
	
}
