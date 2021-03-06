package com.bankapp.employee.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.apache.log4j.Logger;

import com.bankapp.dbutil.PostgresConnection;
import com.bankapp.employee.dao.EmployeeDAO;
import com.bankapp.employee.model.Employee;


public class EmployeeDAOImpl implements EmployeeDAO{
	
	Logger log=Logger.getLogger(EmployeeDAOImpl.class);
	@Override
	public boolean empLogin(Employee employee) {
		int check = 0;
		int empId = employee.getEmpId();
		String empPassword = employee.getPassword();
		try(Connection connection=PostgresConnection.getConnection()){
			String sql ="select * from bank_schema.employee";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next() && check == 0) {
				int realId = resultSet.getInt("empid");
				String realPassword = resultSet.getString("password");
			     if(empId==realId && empPassword.equals(realPassword)) {
			    	 check = 1;
					} 
				}
			} catch (ClassNotFoundException | SQLException e) {
				log.info(e);
			}
			if (check == 1) {
				return true;
			} else {
				return false;
			}

		}
	}
