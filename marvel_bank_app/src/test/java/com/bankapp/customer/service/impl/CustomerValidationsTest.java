package com.bankapp.customer.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CustomerValidationsTest {


	@Test
	void testIsValidEmailWithAt() {
		assertEquals(true, CustomerValidations.isValidEmail("hello@gmail.com"));
	}
	
	@Test
	void testIsValidEmailWithoutAt() {
		assertEquals(false, CustomerValidations.isValidEmail("hellogmail.com"));
	}

	@Test
	void testIsValidCustPasswordNoUpperNoNumber() {
		assertEquals(false, CustomerValidations.isValidCustPassword("password"));
	}
	
	@Test
	void testIsValidCustPasswordNoUpper() {
		assertEquals(false, CustomerValidations.isValidCustPassword("password1"));
	}
	
	@Test
	void testIsValidCustPasswordNoNumber() {
		assertEquals(false, CustomerValidations.isValidCustPassword("Password"));
	}
	
	@Test
	void testIsValidCustPasswordUpperAndNumber() {
		assertEquals(true, CustomerValidations.isValidCustPassword("Password1"));
	}

	@Test
	void testIsValidCustFirstnameTooLong() {
		assertEquals(false, CustomerValidations.isValidCustFirstname("Jamesjamesjamesjames"));
	}
	
	@Test
	void testIsValidCustFirstnameNoUpper() {
		assertEquals(false, CustomerValidations.isValidCustFirstname("james"));
	}
	
	@Test
	void testIsValidCustFirstname() {
		assertEquals(true, CustomerValidations.isValidCustFirstname("James"));
	}

	@Test
	void testIsValidCustLastnameTooLong() {
		assertEquals(false, CustomerValidations.isValidCustLastname("Martinezmartinezmartinezmartinez"));
	}
	
	@Test
	void testIsValidCustLastnameNoUpper() {
		assertEquals(false, CustomerValidations.isValidCustLastname("martinez"));
	}
	
	@Test
	void testIsValidCustLastname() {
		assertEquals(true, CustomerValidations.isValidCustLastname("Martinez"));
	}

	@Test
	void testIsValidAddressTooLong() {
		assertEquals(false, CustomerValidations.isValidAddress("walalalalalalalalalalalalallalalalalalalalalalallalalallalalalallalalalalla"));
	}
	
	@Test
	void testIsValidAddressWithoutComma() {
		assertEquals(true, CustomerValidations.isValidAddress("Brooklyn NY"));
	}

	@Test
	void testIsNotValidCustNumber() {
		assertEquals(false, CustomerValidations.isValidCustNumber("17185538267"));
	}
	
	@Test
	void testIsValidCustNumber() {
		assertEquals(true, CustomerValidations.isValidCustNumber("123-234-1234"));
	}

	@Test
	void testIsNotValidAccountId() {
		assertEquals(false, CustomerValidations.isValidAccountId(20000000));
	}
	
	@Test
	void testIsValidAccountId() {
		assertEquals(true, CustomerValidations.isValidAccountId(200));
	}

}
