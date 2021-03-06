package com.bankapp.customer.service.impl;

public class CustomerValidations { //validations complete but not tested
	
	public static boolean isValidEmail(String email) {
		if (email != null && email.contains("@")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static boolean isValidCustPassword(String password) {
		if (password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")){ //  && password.length() >= 8) { 
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean isValidCustFirstname(String firstname) {
		if (firstname != null && firstname.matches("[A-Z]{1,1}[a-z]{3,15}")) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean isValidCustLastname(String lastname) {
		if (lastname != null && lastname.matches("[A-Z]{1,1}[a-z]{3,25}")) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean isValidAddress(String address) {
		if (address != null && address.length() >= 5 && address.length() < 40) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean isValidCustNumber(String number) {
		if (number != null && number.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}")) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean isValidAccountId(int accountId) {
		if (accountId < 2000 && accountId > 100 && accountId%10 == 0) {
			return true;
		} else {
			return false;
		}

	}
	

}
