package com.bankapp.account.service.impl;

public class AccountValidations {
	
	public static boolean isValidAccountId(int accountId) {
		if (accountId < 2000 && accountId > 100 && accountId%10 == 0) {
			return true;
		} else {
			return false;
		}

	}
	
	public static boolean isValidAccountNumber(int accountNumber) {
		if (accountNumber > 700000001 && accountNumber < 799999999) {
			return true;
		} else {
			return false;
		}

	}
	

}
