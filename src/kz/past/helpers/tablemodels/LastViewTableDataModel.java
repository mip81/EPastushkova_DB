package kz.past.helpers.tablemodels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class LastViewTableDataModel extends AbstractTableModel {
	ArrayList data = new ArrayList();
	ArrayList columnNames = new ArrayList<String>();
	String defFieldNames[] = {"ID", "Дата", "Время", "Клиент", "Телефон", "Процедура", "Тип", "Цена", "Мастер", "%", "Город", "Инфо"};
	
	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public int getRowCount() {
		synchronized (data) {
			return data.size();
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		synchronized (data) {
			return ((ArrayList)data.get(row)).get(col);
		}
	}
	
	@Override
	public String getColumnName(int col) {
		return (String)columnNames.get(col);
	}
/**
 * Заполняет модель таблицы данными
 * если указаны даты как null то 
 * загружается последний отчет за месяц.
 * @param conn
 * @param startDate 
 * @param endDate
 */
	public void setDataSource(Connection conn, Date startDate, Date endDate){
		SimpleDateFormat dOut = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat tOut = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dbData = new SimpleDateFormat("yyyy-MM-dd");
		String  sqlDates ="" ;
				
		String sql = "";
		  
			data.clear();
			columnNames.clear();
	if(startDate == null || endDate == null ){	
		//Узнаем последний месяц отчета
		  try(Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT MAX(procdate) FROM clients")){
			  //JOptionPane.showMessageDialog(null, "Размер равен = "+rs.getFetchSize());
			  if(rs.next()==true){
				  Calendar cal = Calendar.getInstance();
				  cal.setTime(rs.getDate(1));
				   cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); //Ставим первое число в месяце
				  Date firstDate = cal.getTime();
				   cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.getActualMaximum(Calendar.DAY_OF_MONTH)); //Cтавим последнее число в месяце
				  Date lastDate = cal.getTime();
				  
				   sqlDates = " AND clients.procdate BETWEEN '"+dbData.format(firstDate)+"' AND '"+dbData.format(lastDate)+"' ";
				   
			  }else{
				  JOptionPane.showMessageDialog(null, "Ошибка соеденения или записи в БД не обнаружены.");
				  return ;
			  }
		  }catch(Exception e){
			  JOptionPane.showMessageDialog(null, "LastViewTableDataModel. Ошибка при получении даты последнего отчета. "+e);
		  }
	} else  sqlDates = " AND clients.procdate BETWEEN '"+dbData.format(startDate)+"' AND '"+dbData.format(endDate)+"' "; 
			// Иначе указанная дата
	
	
	 sql =  "SELECT clients.id, concat(clients.procdate) as 'Дата',  concat(clients.stime) as 'Время', concat(clients.fio) as 'Клиент', concat(clients.phone) as 'Телефон',"+
				"concat(proc.proc) as 'Процедура', concat(proctypes.type) as 'Тип', concat(clients.price) as 'Цена', concat(masters.fname, ' ',masters.lname) as 'Мастер',"+
		        "concat(clients.pricemaster) as '%', concat(cities.cityname) as 'Город', concat(clients.info) as 'Инфо'"+
		        "FROM clients, proc, proctypes, cities, masters "+
		        "WHERE clients.idproc = proc.id AND clients.idproc = proctypes.id AND clients.idcity = cities.id AND clients.idmaster = masters.id "+
		        sqlDates+
		        " ORDER BY clients.procdate, clients.stime";
			//System.out.println(" "+sql);
		//Получение данных за указанный период	
		   
			try(Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)){
//			  //Получаем имена столбцов
//			  for (int count = 1; count < rs.getMetaData().getColumnCount(); count++){ // Getting names of field from SQL Query
//				  columnNames.add("<html><b>"+rs.getMetaData().getColumnName(count));  // disabled because of problems with encoding
//				 // System.out.println(" --- "+rs.getMetaData().getColumnName(count));
//			  }
			  //fireTableDataChanged(); // Saying that table headers was changed
			  
			  
			  while(rs.next()){
				  synchronized (data) {
					  data.add(new ArrayList(Arrays.asList(rs.getInt(1),dOut.format(rs.getDate(2)), tOut.format(rs.getTime(3)), rs.getString(4), rs.getString(5), 
							  rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), rs.getInt(10), rs.getString(11), rs.getString(12))));					  
				}
				  
			  }
			  
			  columnNames.clear();
			  
			  columnNames.addAll(Arrays.asList(defFieldNames));
			  
			  
		  }catch(Exception e){
			  JOptionPane.showMessageDialog(null, "LastViewTableDataModel ошибка при получении данных. " +e);
		  }finally{
			  try{
			   conn.close();
			  }catch(Exception e){
				  JOptionPane.showMessageDialog(null, "Ошибка при закрытии соеденения. "+e);
			  }
		  }
	}

}
