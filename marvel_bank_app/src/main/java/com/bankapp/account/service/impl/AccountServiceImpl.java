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
		// TODO Auto-generated method stub
		return accDAO.openNewBankAcc(account, accountId);
	}

	@Override
	public List<Account> listBankAccById(int id) throws BankException {
		// TODO Auto-generated method stub
		return accDAO.listBankAccById(id);
	}

	@Override
	public Account getBankAccByNum(int accountNumber) throws BankException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return accDAO.deleteBankAccount(accountNumber);
	}

	@Override
	public int getIdByEmailPassword(String email, String Password) {
		// TODO Auto-generated method stub
		return accDAO.getIdByEmailPassword(email, Password);
	}

	@Override
	public int updateBankAccountNumber(int accountNumber, int newAccountNumber) throws BankException {
		// TODO Auto-generated method stub
		return accDAO.updateBankAccountNumber(accountNumber, newAccountNumber);
	}

	@Override
	public int updateBankAccountStatus(int accountNumber, String newStatus) throws BankException {
		// TODO Auto-generated method stub
		return accDAO.updateBankAccountStatus(accountNumber, newStatus);
	}

}