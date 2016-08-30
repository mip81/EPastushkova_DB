package kz.past.helpers.bean;

import java.util.Date;

public class MasterBean {
	int id;
	String fname;
	String mname;
	String lname;
	String address;
	String cell;
	String phone;
	int salary;
	int typeContract;
	public int getTypeContract() {
		return typeContract;
	}
	public void setTypeContract(int typeContract) {
		this.typeContract = typeContract;
	}
	Date sdatecontract = null;
	Date edatecontract = null;
	String location;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public Date getSdatecontract() {
		return sdatecontract;
	}
	public void setSdatecontract(Date sdatecontract) {
		this.sdatecontract = sdatecontract;
	}
	public Date getEdatecontract() {
		return edatecontract;
	}
	public void setEdatecontract(Date edatecontract) {
		this.edatecontract = edatecontract;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
