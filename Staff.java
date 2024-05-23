package Team_7;

import java.util.Scanner;


public class Staff extends  Person implements Set{
	private String Password;
	private String CMT;
	private int StaffID;
	
	public Staff (int staffID,String password, String name, String phone_number, String cmt, String address ) {
		super(name, address, phone_number);
		this.Password = password;
		this.StaffID = staffID;
		this.CMT = cmt;
	}
	public String getPassword (){ return Password;}
	public String getCMT() {
		return CMT;
	}

	public void setCMT(String cMT) {
		CMT = cMT;
	}

	public int getStaffID() {
		return StaffID;
	}

	public void setStaffID(int staffID) {
		StaffID = staffID;
	}

	@Override
	public void add(Object object) {
		object = (Staff) object;


	}

	@Override
	public void update(Object object) {

	}

	@Override
	public void remove(Object object) {

	}

	@Override
	public String toString () {
		return getStaffID()  + ", "
				+ getName() + ", "
				+ getPhone_numbers() + ", "
				+ getCMT() + ", "
				+ getAddress();
	}
}

