package com.bankapp.transaction.model;

import java.sql.Timestamp;

public class Transaction {
	
	private int accountnumber;
	private String type;
	private int amount;
	private Timestamp dateTime;
	
	
	
	public Transaction() {
		
	}


	public Transaction(int accountnumber, String type, int amount, Timestamp dateTime) {
		super();
		this.accountnumber = accountnumber;
		this.type = type;
		this.amount = amount;
		this.dateTime = dateTime;
	}


	public int getAccountnumber() {
		return accountnumber;
	}


	public void setAccountnumber(int accountnumber) {
		this.accountnumber = accountnumber;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public Timestamp getDateTime() {
		return dateTime;
	}


	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}


	@Override
	public String toString() {
		return String.format("%-9d\t%-8s\t%-20s\t%-30s     ", accountnumber, type, "Amount: $"+amount, dateTime);	

	}


	


	
	

}
