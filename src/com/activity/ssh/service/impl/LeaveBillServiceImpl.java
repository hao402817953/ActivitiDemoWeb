package com.activity.ssh.service.impl;

import java.util.List;

import com.activity.ssh.dao.ILeaveBillDao;
import com.activity.ssh.domain.Employee;
import com.activity.ssh.domain.LeaveBill;
import com.activity.ssh.service.ILeaveBillService;
import com.activity.ssh.utils.SessionContext;

public class LeaveBillServiceImpl implements ILeaveBillService {

	private ILeaveBillDao leaveBillDao;

	public void setLeaveBillDao(ILeaveBillDao leaveBillDao) {
		this.leaveBillDao = leaveBillDao;
	}

	@Override
	public List<LeaveBill> findLeaveBillList() {
		List<LeaveBill> leaveBills = null;
		try {
			Employee employee=SessionContext.get();
			leaveBills = leaveBillDao.findLeaveBillList(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveBills;
	}

	@Override
	public void saveLeaveBill(LeaveBill leaveBill) {
		leaveBill.setUser(SessionContext.get());
		leaveBillDao.saveLeaveBill( leaveBill);
		
	}

	@Override
	public void updateLeaveBill(LeaveBill leaveBill) {
		leaveBillDao.updateLeaveBill( leaveBill);
		
	}

	@Override
	public void deleteLeaveBill(LeaveBill leaveBill) {
		leaveBillDao.deleteLeaveBill( leaveBill);
		
	}

	@Override
	public LeaveBill findOneLeaveBill(Long id) {
		LeaveBill leaveBill=leaveBillDao.findOneLeaveBill( id); 
		return leaveBill;
	}

}
