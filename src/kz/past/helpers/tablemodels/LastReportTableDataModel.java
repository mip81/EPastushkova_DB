package kz.past.helpers.tablemodels;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import kz.past.helpers.Conn;
import kz.past.helpers.DBFunctions;
import kz.past.helpers.bean.ReportBean;

public class LastReportTableDataModel extends AbstractTableModel {
	private ArrayList<String> columnNames;
	private ArrayList  data;
	private boolean editable;
	private Date startDate = null;
	private Date endDate = null;
	
	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public LastReportTableDataModel(Boolean isEditable) {
		this.editable = isEditable;
	}
	
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
	public Object getValueAt(int row, int column) {
		synchronized (data) {
			return ((ArrayList)data.get(row)).get(column);
		}
		
	}
	
	public String getColumnName(int column){
		return (String)columnNames.get(column);
	}
	
	public boolean isEditable(int row, int column){
		return editable;
	}
	/**
	 * Установка данных в модель таблицы
	 * если параметры null то установка
	 * полследнего отчета.
	 * @param sDate
	 * @param eDate
	 */
	public void setData(Date sDate, Date eDate){
		String Master="";
		int sgp = 0; int sgk = 0; int sbp = 0; int sbk = 0; int slp = 0; int slk = 0; int srp = 0; int srk = 0; 
		int ssumproc = 0; int ssummoney =0; int ssumsalary = 0; int sresmoney = 0;
		columnNames = new ArrayList(Arrays.asList("<html><b>Мастер", "<html><b>ГП", "<html><b>ГК", "<html><b>БП", "<html><b>БК", "<html><b>ЛП", "<html><b>ЛК",
									"<html><b>РП", "<html><b>РК", "<html><b>Общее кол-во",  "<html><b>Общ. сумма", "<html><b>Зарплата", "<html><b>Доход"));
		
		
		SimpleDateFormat sbfDbDate = new SimpleDateFormat("yyyy-MM-dd");

		String sqlDates;
		//Если последний отчет то
		if(sDate == null || eDate == null){
			//Узнаем последний месяц отчета и возвращаем startDate и endDate
			  try(Connection conn = Conn.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT MAX(procdate) FROM clients")){
				  //JOptionPane.showMessageDialog(null, "Размер равен = "+rs.getFetchSize());
				  if(rs.next()==true){
					  Calendar cal = Calendar.getInstance();
					  cal.setTime(rs.getDate(1));
					   cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); //Ставим первое число в месяце
					  startDate = cal.getTime();
					   cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.getActualMaximum(Calendar.DAY_OF_MONTH)); //Cтавим последнее число в месяце
					  endDate = cal.getTime();	  
				  }else{
					  JOptionPane.showMessageDialog(null, "Ошибка соеденения или записи в БД не обнаружены.");
					  return ;
				  }
			  }catch(Exception e){
				  JOptionPane.showMessageDialog(null, "LastViewTableDataModel. Ошибка при получении даты последнего отчета. "+e);
			  }
		} else {
			startDate = sDate;
			endDate = eDate;
		}
		
		
		ArrayList<ReportBean> alReportBeans = DBFunctions.getALReport(startDate, endDate);
		data = new ArrayList();
		synchronized (data) {
			if(alReportBeans != null){
				for(ReportBean rB: alReportBeans){
					data.add(new ArrayList(Arrays.asList(rB.getMaster(),
															rB.getGp(),rB.getGk(),
															rB.getBp(),rB.getBk(),
															rB.getLp(), rB.getLk(),
															rB.getRp(),rB.getRk(),
															rB.getSumproc(), rB.getSummoney(),
															rB.getSumsalary(), rB.getResmoney())));
					
					sgp +=rB.getGp(); sgk += rB.getGk();
					sbp += rB.getBp(); sbk +=rB.getBk();
					slp += rB.getLp(); slk += rB.getLk();
					srp += rB.getRp(); srk =+ rB.getRk();
					ssumproc +=rB.getSumproc(); ssummoney += rB.getSummoney();
					ssumsalary += rB.getSumsalary(); sresmoney += rB.getResmoney();
				}
			}
				DecimalFormat decForm = new DecimalFormat("#,###,###");
				String bTag = "<html><b>";
			
			data.add(new ArrayList(Arrays.asList(bTag+"Итого: ", bTag+sgp, bTag+sgk, bTag+sbp, bTag+sbk, bTag+slp, bTag+slk, bTag+srp,
					bTag+srk, bTag+ssumproc, bTag+decForm.format(ssummoney), bTag+decForm.format(ssumsalary), bTag+decForm.format(sresmoney) )));
		}	
		fireTableStructureChanged();
	}

}
