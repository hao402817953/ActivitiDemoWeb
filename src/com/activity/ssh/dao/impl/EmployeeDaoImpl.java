package com.activity.ssh.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.activity.ssh.dao.IEmployeeDao;
import com.activity.ssh.domain.Employee;

public class EmployeeDaoImpl extends HibernateDaoSupport implements IEmployeeDao {

	@Override
	public Employee findEmployeeServiceByName(String name, String password) {
		String hql ="FROM Employee e where e.name=? and e.password=?";
		
		String[] param={name,password};
		List<Employee> list = this.getHibernateTemplate().find(hql, param);
		Employee emp=null;
		if (list!=null&&list.size()>0) {
			emp=list.get(0);
		}
		return emp;
	}

	@Override
	public Integer saveEmployee(Employee employee) {
		Integer flag = null;
		try {
			System.err.println(employee.getName()+"=========--------"+employee.getName()+"=========--------"+employee.getEmail()+"====================4=====================");
			this.getHibernateTemplate().save(employee);
			flag=1;
			System.err.println("=======================5==================");
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}
	
	
}
