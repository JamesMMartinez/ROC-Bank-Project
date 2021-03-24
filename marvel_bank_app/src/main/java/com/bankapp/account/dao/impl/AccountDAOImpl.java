package com.bankapp.account.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bankapp.account.dao.AccountDAO;
import com.bankapp.account.model.Account;
import com.bankapp.dbutil.PostgresConnection;
import com.bankapp.exception.BankException;

public class AccountDAOImpl implements AccountDAO {
	
	Logger log=Logger.getLogger(AccountDAOImpl.class);

	@Override
	public int openNewBankAcc(Account account, int accountId) throws BankException {
		int c = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "insert into bank_schema.account(acctype,accountid) values(?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getAccountType());
			preparedStatement.setInt(2,accountId);
			c = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, occured could not open a new account");
		}//can add new catch to throw new bank exceptions
		return c;
	}

	@Override
	public Account getBankAccByNum(int accountNumber) throws BankException {
		Account account = null;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select a.accountid, a.acctype, a.status, a.balance, a.timestamp, c.firstname, c.lastname \r\n"
					+ "from bank_schema.account a join bank_schema.customer c \r\n"
					+ "on a.accountid = c.accountid where a.accountnumber =?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				account=new Account();
				account.setAccountId(resultSet.getInt("accountid"));
				account.setAccountNumber(accountNumber);
				account.setAccountType(resultSet.getString("acctype"));
				account.setStatus(resultSet.getString("status"));
				account.setBalance(resultSet.getInt("balance"));
				account.setAccountDate(resultSet.getDate("timestamp"));
				account.setOwnerName(resultSet.getString("firstname")+" "+resultSet.getString("lastname"));
			}
			if(account==null) {
				throw new BankException("There are currently no Bank Accounts with Account Number: "+accountNumber);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not retrieve Bank Account");
		}
		return account;
	}


	@Override
	public List<Account> listBankAccById(int accountId) throws BankException {
		List<Account> accountList = new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select a.accountnumber, a.acctype, a.status, a.balance, a.timestamp, c.firstname, c.lastname \r\n"
					+ "from bank_schema.account a join bank_schema.customer c \r\n"
					+ "on a.accountid = c.accountid where a.accountid =?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountId);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account account=new Account();
				account.setAccountId(accountId);
				account.setAccountNumber(resultSet.getInt("accountnumber"));
				account.setAccountType(resultSet.getString("acctype"));
				account.setStatus(resultSet.getString("status"));
				account.setBalance(resultSet.getInt("balance"));
				account.setAccountDate(resultSet.getDate("timestamp"));
				account.setOwnerName(resultSet.getString("firstname")+" "+resultSet.getString("lastname"));
				accountList.add(account);
			}
			if(accountList.size()==0) {
				throw new BankException("There are currently no Bank Accounts with Account ID: "+accountId);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not retrieve Bank Accounts");
		}
		return accountList;
	}

	@Override
	public List<Account> listAllAccounts() throws BankException {
		List<Account> accountList = new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select a.accountnumber, a.accountid, a.acctype, a.status, a.balance, a.timestamp, c.firstname, c.lastname \r\n"
					+ "from bank_schema.account a join bank_schema.customer c \r\n"
					+ "on a.accountid = c.accountid";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account account=new Account();
				account.setAccountId(resultSet.getInt("accountid"));
				account.setAccountNumber(resultSet.getInt("accountnumber"));
				account.setAccountType(resultSet.getString("acctype"));
				account.setStatus(resultSet.getString("status"));
				account.setBalance(resultSet.getInt("balance"));
				account.setAccountDate(resultSet.getDate("timestamp"));
				account.setOwnerName(resultSet.getString("firstname")+" "+resultSet.getString("lastname"));
				accountList.add(account);
			}
			if(accountList.size()==0) {
				throw new BankException("There are currently no Bank Accounts open");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not retrieve Bank Accounts");
		}
		return accountList;
	}

	@Override
	public List<Account> listAccountsByStatus(String status) throws BankException {
		List<Account> accountList = new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select a.accountnumber, a.acctype, a.accountid, a.balance, a.timestamp, c.firstname, c.lastname \r\n"
					+ "from bank_schema.account a join bank_schema.customer c \r\n"
					+ "on a.accountid = c.accountid where status =?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, status);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account account=new Account();
				account.setAccountId(resultSet.getInt("accountid"));
				account.setAccountNumber(resultSet.getInt("accountnumber"));
				account.setAccountType(resultSet.getString("acctype"));
				account.setStatus(status);
				account.setBalance(resultSet.getInt("balance"));
				account.setAccountDate(resultSet.getDate("timestamp"));
				account.setOwnerName(resultSet.getString("firstname")+" "+resultSet.getString("lastname"));
				accountList.add(account);
			}
			if(accountList.size()==0) {
				throw new BankException("There are currently no Bank Accounts that are "+status);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not retrieve Bank Accounts");
		}
		return accountList;
	}


	@Override
	public int getIdByEmailPassword(String email, String password) {
		int accountId = 1;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select accountid from bank_schema.customer where email=? and password=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
			accountId = resultSet.getInt("accountid");
			}

		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
		}
		
		return accountId;
	}

	@Override
	public int updateBankAccountNumber(int accountNumber, int newAccountNumber) throws BankException {
		int c = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			
			String sql = "update bank_schema.account set accountnumber = ? where accountnumber= ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.setInt(1, newAccountNumber);
			c = preparedStatement.executeUpdate();
	
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, bank account could not be updated");
		}//can add new catch to throw new business exception
		return c;
	}

	@Override
	public int updateBankAccountStatus(int accountNumber, String newStatus) throws BankException {
		int c = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			
			String sql = "update bank_schema.account set status = ? where accountnumber= ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(2, accountNumber);
			preparedStatement.setString(1, newStatus);
			c = preparedStatement.executeUpdate();
	
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, Bank Account status could not be updated");
		}
		return c;
	}
	
	@Override
	public int deleteBankAccount(int accountNumber) throws BankException {
			int c = 0;
			try (Connection connection = PostgresConnection.getConnection()) {
				String sql = "delete from bank_schema.account where accountnumber = ? and status = 'Closed'";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, accountNumber);
				c = preparedStatement.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				log.debug(e);
				throw new BankException("Internal error, Bank Account could not be deleted");
			}
			return c;		}

}
