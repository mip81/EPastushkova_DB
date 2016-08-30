package kz.past.helpers;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import kz.past.helpers.bean.ClientBean;
import kz.past.helpers.bean.MasterBean;
import kz.past.helpers.bean.MasterPriceBean;
import kz.past.helpers.bean.ReportBean;

public class DBFunctions {
	static Map<String, Integer> hmProc = null;		// Карта процедур
	static Map<String, Integer> hmMasters = null;	// Карта мастеров
	static Map<String, Integer> hmCities = null;   // Карта городов
	static ArrayList<MasterBean> alMasters = null;
	static ArrayList<MasterPriceBean> alMasterPrice = null;
	
	static SimpleDateFormat sdfDate = new SimpleDateFormat(AppSettings.getKey("dateInMaskCSV"));
	static SimpleDateFormat sdfDateMysql = new SimpleDateFormat(AppSettings.getKey("dateMysqlMask"));
	static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");  // Формат для импорта с Excel
	static SimpleDateFormat sdfOutDate = new SimpleDateFormat(AppSettings.getKey("dateOutMask"));
	



///////////////////////////////////////////	
//     Добавление данных в БД с отчета  ///
///////////////////////////////////////////	
	/**
	 * Read TableModel and prepare ArrayList of beans and insert data into 
	 * Database using insertArrClientsIntoDB(clients, Conn.getConnection());
	 * Метод добавления клиентов  с таблицы в БД
	 * @param tm - Модель таблицы
	 */
	static public void insertDatatoDB(TableModel tm){
	
		ArrayList<ClientBean> clients = new ArrayList<ClientBean>(); //Array for clients table of DB
		boolean isErr = false;
		
		
		int procType;  // '1', 'Первичная' '2', 'Коррекция'	'3', '1 удаление''4', '2 удаление''5', '3 удаление''6', '4 удаление''7', '5 удаление'
		Map<String, Integer> hmProcTypes = getProcType(Conn.getConnection(), false);//new HashMap<String , Integer>();

//		Map<String, Integer> hmProcTypes = new HashMap<String , Integer>();
//			hmProcTypes.put("перв", 1);
//			hmProcTypes.put("кор" , 2);
//			hmProcTypes.put("1 уд" , 3);
//			hmProcTypes.put("2 уд", 4);
//			hmProcTypes.put("3 уд", 5);
//			hmProcTypes.put("4 уд" , 6);
		
		hmProc = getProc(Conn.getConnection(), false); // Получение карты процедур
		hmMasters = getMasters(Conn.getConnection(), true); // Получение карты мастеров
		hmCities = getCities(Conn.getConnection()); // Получение карты городов
		alMasterPrice = getMasterPrice(Conn.getConnection()); // Получение карты оплаты мастеров
		alMasters =  getArrBeanMasters(Conn.getConnection());
		
		int columnCount = tm.getColumnCount();
		int rowCount = tm.getRowCount();
		// Считываем данные с таблицы и создаем массив клиентов
			for (int r = 0; r < rowCount; r++) {    // Working with rows
				ClientBean client = new ClientBean();
				
				for (int c = 0; c < columnCount; c++) {   // Working with columns
					 System.out.print(tm.getValueAt(r, c)+" ");
					try{
						if( tm.getValueAt(r, c) == null ) tm.setValueAt("", r, c);
						
						switch (c) {						
							case 0:	client.setDate(sdfDate.parse(tm.getValueAt(r, c).toString().trim())); 	break;
							case 1:	client.setTime(sdfTime.parse(tm.getValueAt(r, c).toString().trim()));	break;
							case 2:	client.setName(tm.getValueAt(r, c).toString().trim());	break;
							case 3:	client.setPhone(tm.getValueAt(r, c).toString().trim());break;
							case 4:	client.setProc(hmProc.get(tm.getValueAt(r, c).toString().trim().toLowerCase()));break;
							case 5:	client.setType(hmProcTypes.get(tm.getValueAt(r, c).toString().trim().toLowerCase()));break;
							case 6: client.setMaster(hmMasters.get(tm.getValueAt(r, c).toString().trim().toLowerCase())); break;
							case 7: client.setPrice(Integer.parseInt(tm.getValueAt(r, c).toString())); break;
							case 8: client.setInfo(tm.getValueAt(r, c+1).toString().trim()+" "+tm.getValueAt(r, c).toString()); break;
							case 10: client.setCity(hmCities.get(tm.getValueAt(r, c).toString().trim().toLowerCase())); break;
							default:
								break;			
						}
					 }catch(Exception e){
						JOptionPane.showMessageDialog(null, "insertDataToDB: Ошибка при составлении массива клиентов из таблицы. Проверте данные. \n" +
								client.toString()+
								" \n Последняя считанная запись "+tm.getValueAt(r, c)+" "+e.getMessage());
						System.out.println(" Err "+e);
						isErr = true;
						break;
					}
				}
				client.setMasterprice(getPriceOfMaster(alMasters, alMasterPrice, client.getMaster(), client.getProc(), client.getType(), client.getPrice())); // Деньги мастера
				 System.out.println(" -- "+
						 sdfOutDate.format(client.getDate())+", "
									+sdfTime.format(client.getTime())+", "
									+client.getName()+", "
									+client.getPhone()+" , "
									+client.getProc()+", "
									+client.getType()+", "
									+client.getMaster()+", "
									+client.getPrice()+", "
									+client.getMasterprice()+", "
									+client.getKorr()+" "+client.getInfo()+", "
									+client.getCity()+" --- "
						 );
				    clients.add(client); // add client bean into ArrayList
		}
			// if error didn't happen then write beans into database
		if (!isErr) insertArrClientsIntoDB(clients, Conn.getConnection());
	}

///////////////////////////////////////////////////
///// Метод ВСТАВКИ ARRAYLIST CLIENTS В БД //////
///////////////////////////////////////////////////
	/**
	 * Метод ВСТАВКИ ArrayList<ClientBean>  В БД SQL
	 * @param alClientsBean
	 * @param conn
	 */
	static void insertArrClientsIntoDB(ArrayList<ClientBean> alClientsBean, Connection conn){
		
		String sql=null;
		int count = 0;
		try(Statement stmt = conn.createStatement()){
		    for(ClientBean cb : alClientsBean){
				sql="INSERT INTO clients (procdate, stime, fio, phone, idproc, idproctype, idmaster," +
						"price, pricemaster, info, idcity) VALUES (\""
						+sdfDateMysql.format(cb.getDate())+"\", \""
						+sdfTime.format(cb.getTime())+"\", \""
						+cb.getName()+"\", \""
						+cb.getPhone()+"\", \""
						+cb.getProc()+"\", \""
						+cb.getType()+"\", \""
						+cb.getMaster()+"\", \""
						+cb.getPrice()+"\", \""
						+cb.getMasterprice()+"\", \""
						+cb.getKorr()+" "+cb.getInfo()+"\", \""
						+cb.getCity()+"\")";
				 System.out.println(++count+" sql "+sql);
				stmt.execute(sql); 
			}
		   JOptionPane.showMessageDialog(null, alClientsBean.size()+" записей были добавлены в БД.");
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ошибка при импорте БД "+e);
		}finally{
			closeConn(conn);
		}
	}
	
	
///////////////////////////////////////////////////
///////Функция получения доли мастера с процедуры//
///////////////////////////////////////////////////
	/**
	 * Возвращает сумму мастера за процедуру
	 * используется при импорте отчета с Excel для вставки суммы с процедуры для мастера
	 * @param alMasters - массив мастеров
	 * @param alMasterPriceBeans - массив цен на процедуры для мастеров
	 * @param idMaster
	 * @param idProc
	 * @param idProcType
	 * @param price
	 * @return
	 */
	static public int getPriceOfMaster(ArrayList<MasterBean> alMasters, ArrayList<MasterPriceBean> alMasterPriceBeans, int idMaster, int idProc, int idProcType, int price){
		int result = 0;
		int salary = 0;
		int contract;
		for (MasterBean master: alMasters){
			if(master.getId() == idMaster){
				if (master.getTypeContract() == 0) return price;
				if (master.getTypeContract() == 1) return 0;
			}
		}
		for(MasterPriceBean masterPriceBean: alMasterPriceBeans){
			if(masterPriceBean.getIdMaster() == idMaster &&  masterPriceBean.getIdProc() == idProc && masterPriceBean.getIdProcType() == idProcType)
				result = masterPriceBean.getPrice();
			
		}
		if(idProc == 3 ) result = price / 2; // Если процедура ресницы то сумма мастера равна половине
		return result;
	}
/////////////////////////////////////////////	
///////////// СПРАВОЧНИКИ  //////////////////
///////////// Карта мастеров ////////////////
////////////////////////////////////////////	
	/**
	 * Метод получения карты Мастеров 
	 * @param conn
	 * @param reverseIDandNAME используется для реверса фамилии и ID (true STRING, ID; false ID, STRING)
	 * @return
	 */
	static public Map getMasters(Connection conn, boolean reverseIDandNAME){
		alMasters  = new ArrayList<MasterBean>();
		Map hmMasters = null; 
		String sql = "Select id, lname from masters";
		try(Statement stmt =  conn.createStatement(); ResultSet rs  = stmt.executeQuery(sql);){
			if(reverseIDandNAME) hmMasters  = new HashMap<String, Integer>();
			   else hmMasters  = new HashMap<Integer, String>();;
			while(rs.next()){
			   if(reverseIDandNAME) hmMasters.put(rs.getString(2).toLowerCase(), rs.getInt(1));
			   		else hmMasters.put(rs.getInt(1), rs.getString(2).toLowerCase());
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "getMasters: Ошибка получения карты мастеров. "+e);
		}finally{
			closeConn(conn);
		}
		return  hmMasters;
	}
	/**
	 * Метод получения карты мастеров  ID, STRING
	 * @param conn
	 * @return Map
	 */

	static public Map getMasters(Connection conn){
		return getMasters(conn, false);
	}
////////////////////////////////////////////
	
///////////// Карта городов ////////////////
	/**
	 * Метод получения карты города Город ID
	 * @param conn
	 * @return HashMap<String, Integer>
	 */
	static public Map getCities(Connection conn){
		String sql = "Select id, cityname from cities";
		Map hmCities = new HashMap<String, Integer>();
			try(Statement stmt =  conn.createStatement(); ResultSet rs  = stmt.executeQuery(sql);){
				while(rs.next()){
					hmCities.put(rs.getString(2).toLowerCase(), rs.getInt(1));
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "getCities: Ошибка подключения. "+e);
			}finally{
				closeConn(conn);
			}
	return  hmCities;
	}
////////////////////////////////////////////

///////////// Карта процедур ////////////////
	/**
	 * Метод получения карты процедур
	 * @param conn
	 * @return HashMap<String, Integer>
	 */
	static public HashMap getProc(Connection conn, boolean reverseMasterIdToIdMaster){
	 HashMap hmProcedures = null;
	 String sql = "Select id, proc from proc";
	 			
	 		if(reverseMasterIdToIdMaster){
	 			hmProcedures = new HashMap<String, Integer>();
	 		}else{
	 			hmProcedures = new HashMap<Integer, String>();
	 		}
		try(Statement stmt =  conn.createStatement(); ResultSet rs  = stmt.executeQuery(sql);){
			while(rs.next()){
				if(reverseMasterIdToIdMaster){
					hmProcedures.put(rs.getString(2).toLowerCase(), rs.getInt(1));
				}else{
					hmProcedures.put( rs.getInt(1), rs.getString(2).toLowerCase() );
				}
			}
		  }catch(Exception e){
			  JOptionPane.showMessageDialog(null, "getProc: Ошибка подключения. "+e);
		 }finally{
				closeConn(conn);
		 }
	 return  hmProcedures;
	}
	
////////////////////////////////////////////
/**
 * 	Метод получения карты типов процедур
 * @param conn
 * @return HashMap<String, Integer>
 */
///////////// Карта типов процедур ////////////////
	static public HashMap getProcType(Connection conn, boolean reverseIdProc){
		HashMap hmTypeProcedures = null ;
		
			if(reverseIdProc) { hmTypeProcedures = new HashMap<String, Integer>(); }
				else { hmTypeProcedures = new HashMap<Integer, String>(); }
			
		String sql = "Select id, type from proctypes";
			try(Statement stmt =  conn.createStatement(); ResultSet rs  = stmt.executeQuery(sql);){
				while(rs.next()){
					if (!reverseIdProc) hmTypeProcedures.put( rs.getInt(1), rs.getString(2).toLowerCase() );
					if (reverseIdProc) hmTypeProcedures.put( rs.getString(2).toLowerCase() , rs.getInt(1));
				}
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "getTypeProc: Ошибка подключения. "+e);
			}finally{
					closeConn(conn);
			}
		return  hmTypeProcedures;
	}


////////////////////////////////////////////
//    Массив мастеров //////////////////////
////////////////////////////////////////////
	/**
	 * 
	 * Метод полуения массива MasterBean с БД
	 * @param conn
	 * @return ArrayList<MasterBean>
	 */
	static public ArrayList getArrBeanMasters(Connection conn){
		ArrayList<MasterBean> alMasters = new ArrayList<MasterBean>();
		String sql = "SELECT * FROM masters";
		try  ( Statement stmt =  conn.createStatement(); ResultSet rs  = stmt.executeQuery(sql);){
			while (rs.next()){
				MasterBean master = new MasterBean();
				master.setId(rs.getInt("id"));
				master.setFname(rs.getString("fname"));
				master.setMname(rs.getString("mname"));
				master.setLname(rs.getString("lname"));
				master.setAddress(rs.getString("address"));
				master.setCell(rs.getString("cell"));
				master.setPhone(rs.getString("phone"));
				master.setCell(rs.getString("cell"));
				master.setSalary(rs.getInt("masterprice"));
				master.setTypeContract(rs.getInt("typeContract"));
				master.setCell(rs.getString("cell"));
				master.setSdatecontract(rs.getDate("sdatecontract"));
				master.setEdatecontract(rs.getDate("edatecontract"));
				master.setLocation(rs.getString("location"));
	
				alMasters.add(master);
			}
			
		}catch (Exception e) {
			new JOptionPane().showMessageDialog(null, "Ошибка при получении массива мастеров "+e);
		}finally{
				closeConn(conn);
		}
		return alMasters;
		
	}
////////////////////////////////////////////////////

/////////////////////////////////////////////////
// Функция получения ArrayList MasterPriceBean///
/////////////////////////////////////////////////
	/**
	 * Метод получения массива MasterPriceBean's
	 * @param conn
	 * @return  ArrayList<MasterPriceBean>
	 */
	static public  ArrayList getMasterPrice(Connection conn){
		      ArrayList<MasterPriceBean> alMasterPrice = new ArrayList<MasterPriceBean>();
		      String sql = "Select * from masterprice";
		      
		      try ( Statement stmt =  conn.createStatement(); ResultSet rs  = stmt.executeQuery(sql);){
				while(rs.next()){
					MasterPriceBean masterPriceBean = new MasterPriceBean();
					masterPriceBean.setId(rs.getInt("id"));
					masterPriceBean.setIdMaster(rs.getInt("idMaster"));
					masterPriceBean.setIdProc(rs.getInt("idProc"));
					masterPriceBean.setIdProcType(rs.getInt("idProcType"));
					masterPriceBean.setPrice(rs.getInt("price"));
					
					alMasterPrice.add(masterPriceBean);
				}
			} catch (Exception e) {
				new JOptionPane().showMessageDialog(null, "Ошибка при получении MasterPrice таблицы "+e);
			}finally{
				closeConn(conn);
				
			}
		   return alMasterPrice;
	}
	
////////////////////////////////////////////////////
//Функция генерации отчета ArrayList ReportBean/////
////////////////////////////////////////////////////
	static public ArrayList getALReport(Date startDate, Date endDate){
		ArrayList<ReportBean> alReportBeans = new ArrayList<ReportBean>();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		hmMasters = getMasters(Conn.getConnection(), true);
		Collection masterIds = hmMasters.values();
			Iterator<Integer> itr = masterIds.iterator();
			 while (itr.hasNext()) {
				int idMaster = itr.next();
				String sql = "SELECT  clients.idmaster, clients.idproc, clients.idproctype, " +
						"clients.price, clients.pricemaster, masters.fname, masters.lname, " +
						"masters.masterprice,  masters.typecontract " +
						"FROM clients, masters " +
						"WHERE clients.idmaster = masters.id"+" and idmaster = "+idMaster+" and clients.procdate BETWEEN '"+sdfDate.format(startDate)+"' and '"+sdfDate.format(endDate)+"'";
					try(Connection conn = Conn.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
						ReportBean rB = new ReportBean();
						while (rs.next()) {   // Bp = брови первичка Gk = губы коррекция итд
							if(rs.getInt("idproc") == 1 && rs.getInt("idproctype") == 1) rB.setGp(rB.getGp()+1); 
							if(rs.getInt("idproc") == 1 && rs.getInt("idproctype") == 2) rB.setGk(rB.getGk()+1);
							if(rs.getInt("idproc") == 2 && rs.getInt("idproctype") == 1) rB.setBp(rB.getBp()+1);
							if(rs.getInt("idproc") == 2 && rs.getInt("idproctype") == 2) rB.setBk(rB.getBk()+1);
							if(rs.getInt("idproc") == 3 && rs.getInt("idproctype") == 1) rB.setRp(rB.getRp()+1);
							if(rs.getInt("idproc") == 3 && rs.getInt("idproctype") == 2) rB.setRk(rB.getRk()+1);
							if(rs.getInt("idproc") == 4 && rs.getInt("idproctype") == 1) rB.setLp(rB.getLp()+1);
							if(rs.getInt("idproc") == 4 && rs.getInt("idproctype") == 2) rB.setLk(rB.getLk()+1);
							if(rs.getInt("idproc") == 4 && rs.getInt("idproctype") == 3) rB.setLk(rB.getLk()+1);
							if(rs.getInt("idproc") == 4 && rs.getInt("idproctype") == 4) rB.setLk(rB.getLk()+1);
							if(rs.getInt("idproc") == 4 && rs.getInt("idproctype") == 5) rB.setLk(rB.getLk()+1);
							if(rs.getInt("idproc") == 4 && rs.getInt("idproctype") == 6) rB.setLk(rB.getLk()+1);
							rB.setSummoney(rB.getSummoney() + rs.getInt("price"));
							rB.setSumsalary(rB.getSumsalary() + rs.getInt("pricemaster"));		
							rB.setMaster(rs.getString("fname")+" "+rs.getString("lname"));
							if(rs.getInt("typecontract") == 1) rB.setSumsalary(rs.getInt("masterprice"));
						}
						
						
						rB.setSumproc(rB.getBp()+rB.getBk()+rB.getGp()+rB.getGk()+rB.getLp()+rB.getLk()+rB.getRp()+rB.getRk());
						if (idMaster == 1){
							rB.setResmoney(rB.getSummoney());
							rB.setSumsalary(0);
						}
							else rB.setResmoney(rB.getSummoney() - rB.getSumsalary());
						alReportBeans.add(rB);
					
					} catch (Exception e) {
						new JOptionPane().showMessageDialog(null, "Ошибка при генерации отчета "+e);
					}
			 }
	
			return alReportBeans;
	}
///////////////////////////////////////////////////////////////////	
	/**
	 * Метод закрытия Connecion 
	 * @param conn
	 */
	public static void closeConn(Connection conn){
		if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "closeConn: Ошибка закрытия соеденения. "+e);
			}
		}
	}
	//////////////////////////////////
	
	/**
	 * Update field in DB t
	 * @param crs CacheRowSet 
	 * @param row Row in the table
	 * @param col Column in the table
	 * @param val Value which will be written in DB 
	 * @param typeField Type of field if 0 then type is String if 1 then Integer
	 */
	public static void updateField(CachedRowSet crs, int row, int col, Object val, int typeField){
		try{
			crs.absolute(row+1);
			 if(typeField == 0 ) crs.updateString(col+1, (String) val);
			 if(typeField == 1 ) crs.updateInt(col+1, (int) val);
			 crs.updateRow();
			 crs.acceptChanges();
		}catch(Exception e){
			System.out.println("Error in 'updateField' "+e.getMessage());
		}
	}
}
