package com.activity.ssh.dao;

import java.util.List;

import com.activity.ssh.domain.Employee;
import com.activity.ssh.domain.LeaveBill;

public interface ILeaveBillDao {
	/**
	 * 查询自己的请加信息						
	 * @title findLeaveBillList
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @return
	 */
	public  List<LeaveBill> findLeaveBillList(Employee employee);
	/**
	 * 新增请假申请
	 * @title addLeaveBill
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @param leaveBill
	 */
	public void saveLeaveBill(LeaveBill leaveBill);
	/**
	 * 新增请假申请
	 * @title updateLeaveBill
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @param leaveBill
	 */
	public void updateLeaveBill(LeaveBill leaveBill) ;
	/**
	 * 新增请假申请
	 * @title deleteLeaveBill
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @param leaveBill
	 */
	public void deleteLeaveBill(LeaveBill leaveBill);
	
	/**
	 * 根据请假申请Id得到一条请假记录
	 * @title deleteLeaveBill
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @param id
	 * @return
	 */
	public LeaveBill findOneLeaveBill(Long id);



}
