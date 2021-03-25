package com.bankapp.customer.model;

public class Customer {
	
	private String lastName;
	private String firstName;
	private String address;
	private String number;
	private String email;
	private String password;
	private int accountid;
	
	
	public Customer() {


	}


	public Customer(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}



	public Customer(String firstName, String lastName, String address, String number, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.number = number;
		this.email = email;
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}


	@Override
	public String toString() {
		return String.format("%-15s\t%-12s\t%-25s\t%-13s\t%-25s\t%-18s\t%3s     ", lastName, firstName, address, number, email, password, "ID: "+accountid);
	}
	
	

	
}
