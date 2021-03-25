package com.bankapp.account.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AccountValidationsTest {


	@Test
	void testIsNotValidAccountNumber() {
		assertEquals(false, AccountValidations.isValidAccountNumber(100000000));
	}
	
	@Test
	void testIsValidAccountNumber() {
		assertEquals(true, AccountValidations.isValidAccountNumber(778562039));
	}
	
	@Test
	void testIsNotValidAccountId() {
		assertEquals(false, AccountValidations.isValidAccountId(0));
	}
	
	@Test
	void testIsValidAccountId() {
		assertEquals(true, AccountValidations.isValidAccountId(350));
	}

}
