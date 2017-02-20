package com.activity.ssh.dao;

import java.io.Serializable;

import com.activity.ssh.domain.Employee;

public interface IEmployeeDao {
	
	/**
	 * 使用用户名和密码查询数据库是否存在(dao方法)
	 * @param name 用户名
	 * @param password 密码
	 * @return
	 */
	public Employee findEmployeeServiceByName(String name, String password);
	/**
	 * 添加用户信息
	 * @param employee
	 */
	public Integer saveEmployee(Employee employee);

	

}
