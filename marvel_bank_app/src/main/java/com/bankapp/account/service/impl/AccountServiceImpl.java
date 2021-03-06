package com.bankapp.account.service.impl;

import java.util.List;

import com.bankapp.account.dao.AccountDAO;
import com.bankapp.account.dao.impl.AccountDAOImpl;
import com.bankapp.account.model.Account;
import com.bankapp.account.service.AccountService;
import com.bankapp.exception.BankException;

public class AccountServiceImpl implements AccountService{

	private AccountDAO accDAO = new AccountDAOImpl();

	@Override
	public int openNewBankAcc(Account account, int accountId) throws BankException {
//		if(!AccountValidations.isValidAccountId(account.getAccountId())) {
//			throw new BankException("The Account ID: "+account.getAccountId()+" is invalid");
//		}
		return accDAO.openNewBankAcc(account, accountId);
	}

	@Override
	public List<Account> listBankAccById(int accountId) throws BankException {
		if(!AccountValidations.isValidAccountId(accountId)) {
			throw new BankException("The Account ID: "+accountId+" is invalid");
		}
		return accDAO.listBankAccById(accountId);
	}

	@Override
	public Account getBankAccByNum(int accountNumber) throws BankException {
		if(!AccountValidations.isValidAccountNumber(accountNumber)) {
			throw new BankException("The entered Account Number: "+accountNumber+" is invalid or PENDING APPROVAL");
		}
		return accDAO.getBankAccByNum(accountNumber);
	}

	@Override
	public List<Account> listAllAccounts() throws BankException {
		// TODO Auto-generated method stub
		return accDAO.listAllAccounts();
	}

	@Override
	public List<Account> listAccountsByStatus(String status) throws BankException {
		// TODO Auto-generated method stub
		return accDAO.listAccountsByStatus(status);
	}

	@Override
	public int deleteBankAccount(int accountNumber) throws BankException {
		if(!AccountValidations.isValidAccountNumber(accountNumber)) {
			throw new BankException("The entered Account Number: "+accountNumber+" is invalid or PENDING APPROVAL");
		}
		return accDAO.deleteBankAccount(accountNumber);
	}

	@Override
	public int updateBankAccountNumber(int accountNumber, int newAccountNumber) throws BankException {
		if(!AccountValidations.isValidAccountNumber(newAccountNumber)) {
			throw new BankException("The entered Account Number: "+newAccountNumber+" is invalid");
		}
		return accDAO.updateBankAccountNumber(accountNumber, newAccountNumber);
	}

	@Override
	public int updateBankAccountStatus(int accountNumber, String newStatus) throws BankException {
		if(!AccountValidations.isValidAccountNumber(accountNumber)) {
			throw new BankException("The entered Account Number: "+accountNumber+" is invalid");
		}
		return accDAO.updateBankAccountStatus(accountNumber, newStatus);
	}

}
