package kz.past.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParserXLSX {
	
	Vector vTableData = new Vector();
		
	
	
	
	public Vector getVTableData(){
			return vTableData;
	}
	
	
	
	public String getCellString(Cell cell){
		String result = "";
		switch (cell.getCellType()){
			case Cell.CELL_TYPE_BLANK : result = ""; break;
			case Cell.CELL_TYPE_BOOLEAN : result = String.valueOf(cell.getBooleanCellValue()); break;
			case Cell.CELL_TYPE_STRING : result = cell.getStringCellValue(); break;
			case Cell.CELL_TYPE_NUMERIC : result = String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_ERROR: result = "";
			//default: result =""; break;
		}
		
		return result;
	}
	
	public ParserXLSX(String filename){
	    SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		SimpleDateFormat date = new SimpleDateFormat("dd:MM:yyyy");
		InputStream is = null;
	try{
			is = new FileInputStream(filename);
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> itr =  sheet.iterator();
			int sum = 0;
				int numRow = 0;
			while(itr.hasNext()){
				Vector vRowData = new Vector();
					numRow++;
					if(numRow == 1) itr.next();
					Row row =  itr.next();
					
					//System.out.println("Define variables");     
					Cell dateCell = row.getCell(0); 
					Cell timeCell = row.getCell(1); 
						if (timeCell != null) timeCell.setCellType(Cell.CELL_TYPE_NUMERIC);
					Cell clientCell = row.getCell(2);
						if (clientCell != null) clientCell.setCellType(Cell.CELL_TYPE_STRING);
					Cell phoneClientCell = row.getCell(3);
						if (phoneClientCell != null) phoneClientCell.setCellType(Cell.CELL_TYPE_STRING);
					Cell procCell = row.getCell(4); 
						if (procCell != null) procCell.setCellType(Cell.CELL_TYPE_STRING);
					Cell typeProcCell = row.getCell(5); 
						if (typeProcCell != null) typeProcCell.setCellType(Cell.CELL_TYPE_STRING);
					Cell masterCell = row.getCell(6);
						if (masterCell != null) masterCell.setCellType(Cell.CELL_TYPE_STRING);
					Cell priceCell = row.getCell(7);
						if (priceCell != null) priceCell.setCellType(Cell.CELL_TYPE_STRING);
					Cell addInfoCell = row.getCell(8); 
						if (addInfoCell != null) addInfoCell.setCellType(Cell.CELL_TYPE_STRING);
					Cell nextCorrCell = row.getCell(9); 
						if (nextCorrCell != null) nextCorrCell.setCellType(Cell.CELL_TYPE_STRING);
					Cell cityCell = row.getCell(10); 
//						if (nextCorrCell != null) cityCell.setCellType(Cell.CELL_TYPE_STRING);
					
				     // Проверка получаемых данных
					if(dateCell != null && timeCell != null && dateCell.getCellType() != Cell.CELL_TYPE_STRING  && timeCell.getDateCellValue() != null && dateCell.getDateCellValue() != null){
//						System.out.print(" <<< "+ (numRow - 1) + " ");
	
//						 System.out.print("Дата: " + date.format(dateCell.getDateCellValue())+"  ");
						 	 vRowData.add(date.format(dateCell.getDateCellValue()));
//						 System.out.print("Время: " +time.format(timeCell.getDateCellValue())+"  ");
						 	vRowData.add(time.format(timeCell.getDateCellValue()));
//						 System.out.print("Клиент: " +clientCell+"  ");
						 	vRowData.add(clientCell);
//						 System.out.print("Тел: " +phoneClientCell+" ");
						 	vRowData.add(phoneClientCell);
//						 System.out.print("Проц.: " +procCell+"  ");
						 	vRowData.add(procCell);
//						 System.out.print("Тип проц.: " +typeProcCell+"  ");
						 	vRowData.add(typeProcCell);
//						 System.out.print("Мастер.: " +masterCell+"  ");
						 	vRowData.add(masterCell);
//						 System.out.print("Цена: " +priceCell+"  ");
						 	vRowData.add(priceCell);					 	
//						 System.out.print("Прим.: " +addInfoCell);
						 	vRowData.add(addInfoCell);
//						 System.out.println("Корр.: " +nextCorrCell);
						 	vRowData.add(nextCorrCell);
//						 System.out.println("Город: " +cityCell);
						 	vRowData.add(cityCell);

						vTableData.add(vRowData);
						 
					}						
		   } 
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ошибка при импорте отчета в таблицу: "+e);
			System.out.println("Exc:ParserXLSX: "+e);
		}
	
	}
}
