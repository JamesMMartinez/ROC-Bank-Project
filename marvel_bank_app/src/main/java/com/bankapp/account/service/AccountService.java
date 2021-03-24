package com.bankapp.account.service;

import java.util.List;

import com.bankapp.account.model.Account;
import com.bankapp.exception.BankException;

public interface AccountService {
	
	public int openNewBankAcc(Account account, int accountId)throws BankException; //Both? --> ready --> completed
	public Account getBankAccByNum(int accountNumber)throws BankException; //Employee ready --> completed
	
	public List<Account> listBankAccById(int id)throws BankException; //Employee ready --> completed
	public List<Account> listAllAccounts()throws BankException; //Employee ready--> completed
	public List<Account> listAccountsByStatus(String status)throws BankException; //Employee ready --> completed
	
	public int updateBankAccountNumber(int accountNumber, int newAccountNumber) throws BankException; //Employee ready --> completed
	public int updateBankAccountStatus(int accountNumber, String newStatus) throws BankException;//Employee ready --> completed
	public int deleteBankAccount(int accountNumber) throws BankException; //Employee ready --> completed
	
}
