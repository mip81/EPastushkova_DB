package kz.past.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ParserCSV {
	private Vector vTableData = new Vector();
	private String delimiterCSV ="";
	
	public Vector getvTableData() {
		return vTableData;
	}
	
	public ParserCSV(File file) {
		SimpleDateFormat sdfIn = new SimpleDateFormat(AppSettings.getKey("dateInMaskCSV"));
		SimpleDateFormat sdfOut = new SimpleDateFormat(AppSettings.getKey("dateOutMask"));
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String str = ""; // For reading every string from file;
			int countStr = 0;   // For counting strings if the first then miss it;
			String[] arrStr = null; // Array of every line strings 
			boolean flag = false; // if complete record of report then true
				//Reading file into array
				while(( str = br.readLine() ) != null){
					 
					   Vector vData = new Vector<String>(); //For data from report (will be used in defaultTableModel)
					
					if(countStr != 0){
						arrStr = str.split(AppSettings.getKey("delimiterCSV"));
						if( arrStr.length > 2 && !arrStr[0].equals("") && !arrStr[1].equals("") && !arrStr[2].equals("")){
							 int cntStr = 0;
							for(String field :  arrStr){
								if( cntStr == 0 ){
									vData.add( sdfOut.format(sdfIn.parse(field)) );
								}else
								vData.add(field);
								
								System.out.print( field + " " );
								flag = true;
								cntStr++; //count number in array, if 
							}
							cntStr = 0;
							if (flag){
								System.out.println(" N "+countStr);
								vTableData.add(vData);

							}
							flag = false;
						}
						
					}
						
					countStr++; //Count of lines in the file with data	
				}			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "The error occured when CSV file was reading: "+e);
			System.out.println("ParserCSV : Error:"+e);
		}
		
	}

}
