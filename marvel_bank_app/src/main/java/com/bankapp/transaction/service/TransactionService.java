package com.bankapp.transaction.service;

import java.util.List;

import com.bankapp.exception.BankException;
import com.bankapp.transaction.model.Transaction;

public interface TransactionService {
	
	public int performWithdrawal(Transaction transaction) throws BankException; //Customer ready -> completed
	public int performDeposit(Transaction transaction) throws BankException; //Customer ready -> completed 
	public int performTransfer(Transaction transaction, int accountNumber2) throws BankException; //Customer ready -> completed
	public List<Transaction> listTransactionById(int accountid) throws BankException; //Employee ready
	public List<Transaction> listTransactionByAccNum(int accountNumber) throws BankException; //Employee/Both? ready
	public List<Transaction> listAllTransactions()throws BankException; //Employee ready
	

	

}
