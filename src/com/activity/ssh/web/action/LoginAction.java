package com.activity.ssh.web.action;

import com.activity.ssh.domain.Employee;
import com.activity.ssh.service.IEmployeeService;
import com.activity.ssh.utils.CipherUtil;
import com.activity.ssh.utils.SessionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport implements ModelDriven<Employee> {

	private Employee employee = new Employee();
	
	private String message;
	/**
	 * 登录
	 * @return
	 */
	public String login(){
		String name=employee.getName();
		String password=CipherUtil.generatePassword(employee.getPassword());
		Employee emp = employeeService.findEmployeeServiceByName(name,password);
		if (emp!=null) {
			SessionContext.setUser(emp);
		} else {
			message="用户名或密码错误,请重新登录";
			return "login";
		}
		return "success";
	}
	/**
	 * 注册页面
	 * @title register
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @return
	 */
	public String register(){
		
			try {
				employee.setPassword(CipherUtil.generatePassword(employee.getPassword()));
				Integer flag = employeeService.saveEmployee(employee);
				if (flag==null) {
					message="注册失败";
					return "register";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return"login";
	}
	
	/**
	 * 标题
	 * @return
	 */
	public String top() {
		return "top";
	}
	
	/**
	 * 左侧菜单
	 * @return
	 */
	public String left() {
		return "left";
	}
	
	/**
	 * 主页显示
	 * @return
	 */
	public String welcome() {
		return "welcome";
	}
	
	public String logout(){
		SessionContext.setUser(null);
		return "login";
	}
	
	
	
	//====================================get方法=====set方法======================================================================================================
	@Override
	public Employee getModel() {
		return employee;
	}
	
	private IEmployeeService employeeService;

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public String getMessage() {
		return message;
	}
	
	
	
	
	
	
	
	
	
}
