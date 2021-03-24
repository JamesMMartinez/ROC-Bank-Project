package com.bankapp.transaction.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bankapp.dbutil.PostgresConnection;
import com.bankapp.exception.BankException;
import com.bankapp.transaction.dao.TransactionDAO;
import com.bankapp.transaction.model.Transaction;

public class TransactionDAOImpl implements TransactionDAO{
	
	Logger log=Logger.getLogger(TransactionDAOImpl.class);

	@Override
	public int performWithdrawal(Transaction transaction) throws BankException {
		int ar= 0;
		Connection connection = null;
		try {
		    connection = PostgresConnection.getConnection();
			String sql1 = "insert into bank_schema.transactions(accountnumber,type,amount) values(?,?,?)";
			String sql2 = "update bank_schema.account set balance=balance - ? where accountnumber = ?";
			connection.setAutoCommit(false);
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setInt(1, transaction.getAccountnumber());
			preparedStatement.setString(2, "Withdrawal");
			preparedStatement.setInt(3, transaction.getAmount());
			preparedStatement.addBatch();
			ar = preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setInt(1, transaction.getAmount());
			preparedStatement.setInt(2, transaction.getAccountnumber());
			preparedStatement.addBatch();
			ar = preparedStatement.executeUpdate();
			connection.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				log.debug(e);
				connection.rollback();
				log.debug("rollback success");
			} catch (SQLException e1) {
				log.debug("unable to rollback");
			}
			throw new BankException("Failure to complete Withdrawal there may be insufficient funds");
		}
		return ar;
	}
	
	@Override
	public int performDeposit(Transaction transaction) throws BankException {
		int ar= 0;
		Connection connection = null;
		try {
		    connection = PostgresConnection.getConnection();
			String sql1 = "insert into bank_schema.transactions(accountnumber,type,amount) values(?,?,?)";
			String sql2 = "update bank_schema.account set balance=balance + ? where accountnumber = ?";
			connection.setAutoCommit(false);
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setInt(1, transaction.getAccountnumber());
			preparedStatement.setString(2, "Deposit   ");
			preparedStatement.setInt(3, transaction.getAmount());
			preparedStatement.addBatch();
			ar = preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setInt(1, transaction.getAmount());
			preparedStatement.setInt(2, transaction.getAccountnumber());
			preparedStatement.addBatch();
			ar = preparedStatement.executeUpdate();
			connection.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				log.debug(e);
				connection.rollback();
				log.debug("rollback success");
			} catch (SQLException e1) {
				log.debug("unable to rollback");
			}
			throw new BankException("Failure to complete Deposit");
		}
		return ar;
	}

	@Override
	public int performTransfer(Transaction transaction, int accountNumber2) throws BankException {
		int ar= 0;
		Connection connection = null;
		try {
		    connection = PostgresConnection.getConnection();
			String sql1 = "insert into bank_schema.transactions(accountnumber,type,amount) values(?,?,?)";
			String sql2 = "update bank_schema.account set balance=balance - ? where accountnumber = ?";
			String sql3 = "update bank_schema.account set balance=balance + ? where accountnumber = ?";
			connection.setAutoCommit(false);
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql1);
			preparedStatement.setInt(1, transaction.getAccountnumber());
			preparedStatement.setString(2, "Transfer  ");
			preparedStatement.setInt(3, transaction.getAmount());
			preparedStatement.addBatch();
			ar = preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(sql2);
			preparedStatement.setInt(1, transaction.getAmount());
			preparedStatement.setInt(2, transaction.getAccountnumber());
			preparedStatement.addBatch();
			ar = preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(sql3);
			preparedStatement.setInt(1, transaction.getAmount());
			preparedStatement.setInt(2, accountNumber2);
			preparedStatement.addBatch();
			ar = preparedStatement.executeUpdate();
			
			connection.commit();
		} catch (ClassNotFoundException | SQLException e) {
			try {
				log.debug(e);
				connection.rollback();
				log.debug("rollback success");
			} catch (SQLException e1) {
				log.debug("unable to rollback");
			}
			throw new BankException("Failure to complete Transfer there may be insufficient funds");
		}
		return ar;
	}

	@Override
	public List<Transaction> listTransactionById(int accountId) throws BankException {
		List<Transaction> tranList = new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select a.accountnumber, t.type, t.amount, t.timestamp\r\n"
					+ "from bank_schema.account a join bank_schema.transactions t\r\n"
					+ "on a.accountnumber = t.accountnumber where a.accountid = ?"; //do a join
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountId);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transaction transaction =new Transaction();
				transaction.setAccountnumber(resultSet.getInt("accountnumber"));
				transaction.setType(resultSet.getString("type"));
				transaction.setAmount(resultSet.getInt("amount"));
				transaction.setDateTime(resultSet.getTimestamp("timestamp"));
				tranList.add(transaction);
			}
			if(tranList.size()==0) {
				throw new BankException("There are currently no transactions with this account id");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error the proccess could not be completed");
		}
		return tranList;
	}

	@Override
	public List<Transaction> listTransactionByAccNum(int accountNumber) throws BankException {
		List<Transaction> tranList = new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select accountnumber, type, amount, timestamp from bank_schema.transactions where accountnumber = ?";		
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountNumber);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transaction transaction =new Transaction();
				transaction.setAccountnumber(resultSet.getInt("accountnumber"));
				transaction.setType(resultSet.getString("type"));
				transaction.setAmount(resultSet.getInt("amount"));
				transaction.setDateTime(resultSet.getTimestamp("timestamp"));
				tranList.add(transaction);
			}
			if(tranList.size()==0) {
				throw new BankException("There are currently no transactions with this account number");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error the proccess could not be completed");
		} //add new catches for bank exceptions
		return tranList;
	}

	@Override
	public List<Transaction> listAllTransactions() throws BankException {
		List<Transaction> tranList = new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select accountnumber, type, amount, timestamp from bank_schema.transactions";		
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transaction transaction =new Transaction();
				transaction.setAccountnumber(resultSet.getInt("accountnumber"));
				transaction.setType(resultSet.getString("type"));
				transaction.setAmount(resultSet.getInt("amount"));
				transaction.setDateTime(resultSet.getTimestamp("timestamp"));
				tranList.add(transaction);
			}
			if(tranList.size()==0) {
				throw new BankException("There are currently no transactions in the database");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error the proccess could not be completed");
		}
		return tranList;
	}

	
}
