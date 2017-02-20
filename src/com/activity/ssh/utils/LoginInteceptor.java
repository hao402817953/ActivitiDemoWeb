package com.activity.ssh.utils;

import com.activity.ssh.domain.Employee;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


/**
 * 登录验证拦截器
 *
 */
@SuppressWarnings("serial")
public class LoginInteceptor implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
	/**
	 * 执行action之前都会访问这个借口
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("==============================未登录拦截器============================");
		Employee employee =SessionContext.get();
		String actionName=invocation.getProxy().getActionName();
		if(!"loginAction_login".equals(actionName)){
			if (employee==null) {
				return "login";
			}
		}
		return invocation.invoke();
		
	}

}
