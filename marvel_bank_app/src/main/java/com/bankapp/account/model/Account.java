package com.bankapp.account.model;

import java.sql.Date;

public class Account {
	
	private int accountNumber;
    private String accountType;
    private String status;
    private float balance;
    private int accountId;    
    private Date accountDate;
    private String holderName;

	public Account() {
	
	}

	public Account(int accountNumber, String accountType, String status, float balance, int accountId, Date accountDate,
			String holderName) {
		super();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.status = status;
		this.balance = balance;
		this.accountId = accountId;
		this.accountDate = accountDate;
		this.holderName = holderName;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public String getOwnerName() {
		return holderName;
	}

	public void setOwnerName(String holderName) {
		this.holderName = holderName;
	}

	@Override
	public String toString() {
		return String.format("%-9d\t%-8s\t%-8s\t%-20s\t%-10s\t%-10s\t%-30s     ", accountNumber, accountType, status, "Balance: $"+balance, "ID: "+accountId, accountDate, "Holder: "+holderName);
	}

	

	
	
	







	
    

}
