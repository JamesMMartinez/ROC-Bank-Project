package com.bankapp.customer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.bankapp.customer.dao.CustomerDAO;
import com.bankapp.customer.model.Customer;
import com.bankapp.dbutil.PostgresConnection;
import com.bankapp.exception.BankException;
import com.bankapp.exception.LoginException;

public class CustomerDAOImpl implements CustomerDAO{
	
	Logger log=Logger.getLogger(CustomerDAOImpl.class);

	@Override
	public int createNewCustAcc(Customer customer) throws LoginException {
		int c = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "insert into bank_schema.customer(firstname,lastname,address,number,email,password) values(?,?,?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getAddress());
			preparedStatement.setString(4, customer.getNumber());
			preparedStatement.setString(5, customer.getEmail());
			preparedStatement.setString(6, customer.getPassword());
			c = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new LoginException("Internal error, Customer Account could not be created");
		}//can add new catch to throw new bank exception 
		return c;
	}

	@Override
	public boolean custLogin(String email, String password) throws LoginException {
		int check = 0;
		try(Connection connection=PostgresConnection.getConnection()){
			String sql ="select email, password from bank_schema.customer";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next() && check == 0) {
				String realEmail = resultSet.getString("email");
				String realPassword = resultSet.getString("password");
			     if(email.equals(realEmail) && password.equals(realPassword)) {
			    	 check = 1;
					} 
				}
			} catch (ClassNotFoundException | SQLException e) {
				log.debug(e);
				throw new LoginException("Internal error, unable to complete login");
			}
			if (check == 1) {
				return true;
			}else {
				return false;
			}

}

	@Override
	public Customer getCustomerAccByid(int accountid) throws BankException {
		Customer customer = null;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select firstname,lastname,address,number,email,password from bank_schema.customer where accountid=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountid);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				customer=new Customer();
				customer.setAccountid(accountid);
				customer.setFirstName(resultSet.getString("firstname"));
				customer.setLastName(resultSet.getString("lastname"));
				customer.setAddress(resultSet.getString("address"));
				customer.setNumber(resultSet.getString("number"));
				customer.setEmail(resultSet.getString("email"));
				customer.setPassword(resultSet.getString("password"));
			}
			if(customer==null) {
				throw new BankException("No customer found with  account ID : "+accountid);
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not get Customer Account");
		}
		return customer;
	}

	@Override
	public int updateCustomerAccByid(int accountid, int choice, String update) throws BankException {
		int c = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			
			String sqlfn = "update bank_schema.customer set firstname = ? where accountid= ?";
			String sqlln = "update bank_schema.customer set lastname = ? where accountid= ?";
			String sqlad = "update bank_schema.customer set address = ? where accountid= ?";
			String sqlnu = "update bank_schema.customer set number = ? where accountid= ?";
			String sqlem = "update bank_schema.customer set email = ? where accountid= ?";
			String sqlpa = "update bank_schema.customer set password = ? where accountid= ?";
			String sql =null;
			if(choice == 1) {
				sql = sqlfn;
			}else if(choice ==2) {
				sql = sqlln;
			}else if(choice ==3) {
				sql = sqlad;
			}else if(choice ==4) {
				sql = sqlnu;
			}else if(choice ==5) {
				sql = sqlem;
			}else if(choice ==6) {
				sql = sqlpa;
			}
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(2, accountid);
			preparedStatement.setString(1, update);
			c = preparedStatement.executeUpdate();
	
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not update Customer Account be sure to select the correct option");
		}
		return c;
	}

	@Override
	public int deleteCustomerAccByid(int accountid) throws BankException {
		int c = 0;
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "delete from bank_schema.customer where accountid = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, accountid);
			c = preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not delete Customer Account be sure corect ID was entered");
		}
		return c;
	}

	@Override
	public List<Customer> listAllCustAccounts() throws BankException {
		List<Customer> customerList = new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select * from bank_schema.Customer order by lastname";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customer customer = new Customer();
				customer.setAccountid(resultSet.getInt("accountid"));
				customer.setFirstName(resultSet.getString("firstname"));
				customer.setLastName(resultSet.getString("lastname"));
				customer.setAddress(resultSet.getString("address"));
				customer.setNumber(resultSet.getString("number"));
				customer.setEmail(resultSet.getString("email"));
				customer.setPassword(resultSet.getString("password"));
				customerList.add(customer);
			}
			if(customerList.size()==0) {
				throw new BankException("No Customer Accounts found in the database");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not list Customer Accounts");
		}
		return customerList;
	}

	@Override
	public List<Customer> listCustByLastName(String lastName) throws BankException {
		List<Customer> customerList = new ArrayList<>();
		try (Connection connection = PostgresConnection.getConnection()) {
			String sql = "select * from bank_schema.customer where lastname=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, lastName);
			
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customer customer=new Customer();
				customer.setAccountid(resultSet.getInt("accountid"));
				customer.setFirstName(resultSet.getString("firstname"));
				customer.setLastName(lastName);
				customer.setAddress(resultSet.getString("address"));
				customer.setNumber(resultSet.getString("number"));
				customer.setEmail(resultSet.getString("email"));
				customer.setPassword(resultSet.getString("password"));
				customerList.add(customer);
			}
			if(customerList.size()==0) {
				throw new BankException("No customer found with the last name \""+lastName+"\"");
			}
		} catch (ClassNotFoundException | SQLException e) {
			log.debug(e);
			throw new BankException("Internal error, could not list Customer Accounts be sure name is correct");
		}
		return customerList;
	}
}
