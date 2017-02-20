package com.activity.ssh.web.action;

import java.util.List;

import com.activity.ssh.domain.LeaveBill;
import com.activity.ssh.service.ILeaveBillService;
import com.activity.ssh.utils.ValueContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class LeaveBillAction extends ActionSupport implements ModelDriven<LeaveBill> {

	private LeaveBill leaveBill = new LeaveBill();
	
	@Override
	public LeaveBill getModel() {
		return leaveBill;
	}
	private LeaveBill  bill;
	private ILeaveBillService leaveBillService;

	public void setLeaveBillService(ILeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	/**
	 * 请假管理首页显示
	 * @return
	 */
	public String home(){
		List<LeaveBill> list=leaveBillService.findLeaveBillList();
		ValueContext.putValueContext("list", list);
		return "home";
	}
	
	/**
	 * 添加请假申请
	 * @return
	 */
	public String input(){
		LeaveBill bill=leaveBillService.findOneLeaveBill(leaveBill.getId());
		ValueContext.putValueStack(bill);
		
		
		return "input";
	}
	
	/**
	 * 保存/更新，请假申请
	 * 
	 * */
	public String save() {
		Long id=leaveBill.getId();
		if (id==null) {
			leaveBillService.saveLeaveBill(leaveBill);
		} else {
			leaveBillService.updateLeaveBill(leaveBill);
		}
		return "save";
	}
	
	/**
	 * 删除，请假申请
	 * 
	 * */
	public String delete(){
		leaveBillService.deleteLeaveBill(leaveBill);
		return "save";
	}

	//======================================================================
	public LeaveBill getBill() {
		return bill;
	}
}
