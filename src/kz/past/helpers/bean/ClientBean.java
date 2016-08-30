package kz.past.helpers.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import kz.past.helpers.AppSettings;

public class ClientBean {
	SimpleDateFormat sdfDateOut = new SimpleDateFormat(AppSettings.getKey("dateMysqlMask"));
	SimpleDateFormat sdfTimeOut = new SimpleDateFormat(AppSettings.getKey("timeMask"));
	Date date = new Date();
	Date time = new Date();
	String name = new String();
	String phone = new String();
	int proc;
	int type;
	int master;
	int price;
	int masterprice;
	
	public int getMasterprice() {
		return masterprice;
	}
	public void setMasterprice(int masterprice) {
		this.masterprice = masterprice;
	}
	String info = new String();
	String korr = new String();
	int city ;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getProc() {
		return proc;
	}
	public void setProc(int proc) {
		this.proc = proc;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getMaster() {
		return master;
	}
	public void setMaster(int master) {
		this.master = master;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getKorr() {
		return korr;
	}
	public void setKorr(String korr) {
		this.korr = korr;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public String toString(){
		return ("Дата: "+sdfDateOut.format(date)+" Время: "+sdfTimeOut.format(time)+" Имя: "+name+" тел: "+phone+" проц.: "+proc+" тип: "+type+" мастер: "+master+" цена: "+price+" мастеру: "+masterprice);			
	}
	

	
}
