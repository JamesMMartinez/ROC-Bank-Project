package com.bankapp.customer.dao;

import java.util.List;

import com.bankapp.customer.model.Customer;
import com.bankapp.exception.BankException;
import com.bankapp.exception.LoginException;

public interface CustomerDAO {
	
	public int createNewCustAcc(Customer customer) throws LoginException;//Customer-> ready ->complete
	public boolean custLogin(String email, String password) throws LoginException; //Customer->ready->complete
	
	public Customer getCustomerAccByid(int accountid)throws BankException; //Employee->ready->complete
	public int updateCustomerAccByid(int accountid, int choice, String update)throws BankException;//Employee->ready->complete
	public int deleteCustomerAccByid(int accountid)throws BankException;//Employee->ready->complete
	public List<Customer> listAllCustAccounts()throws BankException; //Employee->ready->complete
	public List<Customer> listCustByLastName(String lastName)throws BankException;//Employee->ready->complete

}
