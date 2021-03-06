package com.bankapp.transaction.service.impl;

import java.util.List;

import com.bankapp.account.service.impl.AccountValidations;
import com.bankapp.exception.BankException;
import com.bankapp.transaction.dao.TransactionDAO;
import com.bankapp.transaction.dao.impl.TransactionDAOImpl;
import com.bankapp.transaction.model.Transaction;
import com.bankapp.transaction.service.TransactionService;

public class TransactionServiceImpl implements TransactionService{
	
	private TransactionDAO transDAO = new TransactionDAOImpl();

	@Override
	public int performWithdrawal(Transaction transaction) throws BankException {
		if(!TransactionValidations.isValidAccountNumber(transaction.getAccountnumber())) {
			throw new BankException("Unable to perform Withdrawal, Account Number: "+transaction.getAccountnumber()+" is invalid or PENDING APPROVAL");
		}
		if(!TransactionValidations.isValidAmount(transaction.getAmount())) {
			throw new BankException("Unable to perform Withdrawal, Amount cannot be 0 or negative");
		}
		return transDAO.performWithdrawal(transaction);
	}
	
	@Override
	public int performDeposit(Transaction transaction) throws BankException {
		if(!TransactionValidations.isValidAccountNumber(transaction.getAccountnumber())) {
			throw new BankException("Unable to perform Deposit, Account Number: "+transaction.getAccountnumber()+" is invalid or PENDING APPROVAL");
		}
		if(!TransactionValidations.isValidAmount(transaction.getAmount())) {
			throw new BankException("Unable to perform Deposit, Amount cannot be 0 or negative");
		}
		return transDAO.performDeposit(transaction);
	}

	@Override
	public int performTransfer(Transaction transaction, int accountNumber2) throws BankException {
		if(!TransactionValidations.isValidAccountNumber(transaction.getAccountnumber())) {
			throw new BankException("Unable to perform Transfer, Account Number: "+transaction.getAccountnumber()+" is invalid or PENDING APPROVAL");
		}
		if(!TransactionValidations.isValidAmount(transaction.getAmount())) {
			throw new BankException("Unable to perform Transfer, Amount cannot be 0 or negative");
		}
		return transDAO.performTransfer(transaction, accountNumber2);
	}

	@Override
	public List<Transaction> listTransactionById(int accountId) throws BankException {
		if(!TransactionValidations.isValidAccountId(accountId)) {
			throw new BankException("The Account ID: "+accountId+" is invalid");
		}
		return transDAO.listTransactionById(accountId);
	}

	@Override
	public List<Transaction> listTransactionByAccNum(int accountNumber) throws BankException {
		if(!AccountValidations.isValidAccountNumber(accountNumber)) {
			throw new BankException("The entered Account Number: "+accountNumber+" is invalid or PENDING APPROVAL");
		}
		return transDAO.listTransactionByAccNum(accountNumber);
	}

	@Override
	public List<Transaction> listAllTransactions() throws BankException {
		// TODO Auto-generated method stub
		return transDAO.listAllTransactions();
	}


}
