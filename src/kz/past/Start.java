package kz.past;

import javax.swing.UIManager;

import com.mysql.jdbc.Connection;

import kz.past.forms.MainForm;
import kz.past.helpers.Conn;
import kz.past.helpers.DBFunctions;



public class Start {
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		} 

		
			new MainForm();
	try{
		switch (Conn.getConnection().getTransactionIsolation()) {
		case Connection.TRANSACTION_NONE: System.out.println("Connection.TRANSACTION_NONE"); break;
		case Connection.TRANSACTION_READ_COMMITTED: System.out.println("Connection.TRANSACTION_READ_COMMITTED"); break;
		case Connection.TRANSACTION_READ_UNCOMMITTED: System.out.println("Connection.TRANSACTION_READ_UNCOMMITTED"); break;
		case Connection.TRANSACTION_REPEATABLE_READ: System.out.println("Connection.TRANSACTION_REPEATABLE_READ"); break;
		case Connection.TRANSACTION_SERIALIZABLE: System.out.println("Connection.TRANSACTION_REPEATABLE_READ"); break; 
		default: System.out.println("Unknown transaction.") ;break;
		}
	}catch(Exception e){
		System.out.println(" err "+e);
	}
		
			
	}
}
