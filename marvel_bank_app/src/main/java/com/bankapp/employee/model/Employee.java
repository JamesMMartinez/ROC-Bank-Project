package com.bankapp.employee.model;

public class Employee {
	
	private int empId;
	private String password;
	
	public int getEmpId() {
		return empId;
	}


	public void setEmpId(int empId) {
		this.empId = empId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", password=" + password + "]";
	}
	
	
	
	}
