package com.activity.ssh.service.impl;

import java.io.Serializable;

import com.activity.ssh.dao.IEmployeeDao;
import com.activity.ssh.domain.Employee;
import com.activity.ssh.service.IEmployeeService;

public class EmployeeServiceImpl implements IEmployeeService {

	private IEmployeeDao employeeDao;

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public Employee findEmployeeServiceByName(String name, String password) {
		Employee emp=employeeDao.findEmployeeServiceByName(name,password);
		return emp;
	}

	@Override
	public Integer saveEmployee(Employee employee) {
		Integer flag =0;
		try {
			 flag = employeeDao.saveEmployee(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	
}
