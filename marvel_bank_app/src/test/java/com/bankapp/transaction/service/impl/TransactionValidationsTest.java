package com.bankapp.transaction.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TransactionValidationsTest {

	@Test
	void testIsNotValidAccountNumber() {
		assertEquals(false, TransactionValidations.isValidAccountNumber(1297913973));
	}
	
	@Test
	void testIsValidAccountNumber() {
		assertEquals(true, TransactionValidations.isValidAccountNumber(708642008));
	}
	
	@Test
	void testIsNotValidAccountId() {
		assertEquals(false, TransactionValidations.isValidAccountId(202));
	}
	
	@Test
	void testIsValidAccountId() {
		assertEquals(true, TransactionValidations.isValidAccountId(500));
	}

	@Test
	void testIsValidAmountZero() {
		assertEquals(false, TransactionValidations.isValidAmount(0));
		}
	
	@Test
	void testIsValidAmountNeg() {
		assertEquals(false, TransactionValidations.isValidAmount(-500));
		}
	
	@Test
	void testIsValidAmount() {
		assertEquals(true, TransactionValidations.isValidAmount(500));
		}
}
