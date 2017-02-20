package com.activity.ssh.service;

import com.activity.ssh.domain.Employee;

public interface IEmployeeService {
	/**
	 * 使用用户名和密码查询数据库是否存在(业务方法)
	 * @param name 用户名
	 * @param password 密码
	 * @return
	 */
	public Employee findEmployeeServiceByName(String name, String password);
	/**
	 * 用户注册
	 * @param employee
	 */
	public Integer saveEmployee(Employee employee);
	
	
	 

}
