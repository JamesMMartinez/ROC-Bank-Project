package com.bankapp.customer.service.impl;

import java.util.List;

import com.bankapp.customer.dao.CustomerDAO;
import com.bankapp.customer.dao.impl.CustomerDAOImpl;
import com.bankapp.customer.model.Customer;
import com.bankapp.customer.service.CustomerService;
import com.bankapp.exception.BankException;
import com.bankapp.exception.LoginException;

public class CustomerServiceImpl implements CustomerService{
	
	private CustomerDAO custDAO = new CustomerDAOImpl();

	@Override
	public int createNewCustAcc(Customer customer) throws LoginException {
		if(!CustomerValidations.isValidCustFirstname(customer.getFirstName())) {
			throw new LoginException("Invalid First Name format must be between 3-15 characters starting with capital");
		}
		if(!CustomerValidations.isValidCustLastname(customer.getLastName())) {
			throw new LoginException("Invalid Last Name format must be between 3-25 characters starting with capital");
		}
		if(!CustomerValidations.isValidAddress(customer.getAddress())) {
			throw new LoginException("Invalid Address format must be betweem 5-40 characters");
		}
		if(!CustomerValidations.isValidCustNumber(customer.getNumber())) {
			throw new LoginException("Invalid Phone Number format must follow XXX-XXX-XXXX format, exclude country code");
		}
		if(!CustomerValidations.isValidEmail(customer.getEmail())) {
			throw new LoginException("Invalid Email format be sure email exists");
		}
		if(!CustomerValidations.isValidCustPassword(customer.getPassword())) {
			throw new LoginException("Invalid Password format must be at least 8 characters containing at least one lowercase, uppercase, and number");
		}
		
		return custDAO.createNewCustAcc(customer);
	}

	@Override
	public boolean custLogin(String email, String password) throws LoginException {
		// TODO Auto-generated method stub
		return custDAO.custLogin(email, password);
	}

	@Override
	public Customer getCustomerAccByid(int accountId) throws BankException {
		if(!CustomerValidations.isValidAccountId(accountId)) {
			throw new BankException("The Account ID: "+accountId+" is invalid");
		}
		return custDAO.getCustomerAccByid(accountId);
	}

	@Override
	public List<Customer> listAllCustAccounts() throws BankException {
		// TODO Auto-generated method stub
		return custDAO.listAllCustAccounts();
	}

	@Override
	public int updateCustomerAccByid(int accountId, int choice, String update) throws BankException {
		if(!CustomerValidations.isValidAccountId(accountId)) {
			throw new BankException("The Account ID: "+accountId+" is invalid");
		}
		return custDAO.updateCustomerAccByid(accountId, choice, update);
	}

	@Override
	public int deleteCustomerAccByid(int accountId) throws BankException {
		if(!CustomerValidations.isValidAccountId(accountId)) {
			throw new BankException("The Account ID: "+accountId+" is invalid");
		}
		return custDAO.deleteCustomerAccByid(accountId);
	}

	@Override
	public List<Customer> listCustByLastName(String lastName) throws BankException{
		if(!CustomerValidations.isValidCustLastname(lastName)){
			throw new BankException("Invalid Last Name format must be between 3-25 characters starting with capital");
		}
		return custDAO.listCustByLastName(lastName);
	}
	
}
