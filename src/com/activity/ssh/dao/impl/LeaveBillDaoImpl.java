package com.activity.ssh.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.activity.ssh.dao.ILeaveBillDao;
import com.activity.ssh.domain.Employee;
import com.activity.ssh.domain.LeaveBill;
import com.activity.ssh.utils.SessionContext;

public class LeaveBillDaoImpl extends HibernateDaoSupport implements ILeaveBillDao {

	@Override
	public List<LeaveBill> findLeaveBillList(Employee employee) {
		String hql="FROM LeaveBill o where o.user=?";
		List<LeaveBill> leaveBills=this.getHibernateTemplate().find(hql,employee);
		return leaveBills;
	}

	@Override
	public void saveLeaveBill(LeaveBill leaveBill) {
		this.getHibernateTemplate().save(leaveBill);
		
	}
	@Override
	public void updateLeaveBill(LeaveBill leaveBill) {
		this.getHibernateTemplate().update(leaveBill);
		
	}
	@Override
	public void deleteLeaveBill(LeaveBill leaveBill) {
		this.getHibernateTemplate().delete(leaveBill);
		
	}

	@Override
	public LeaveBill findOneLeaveBill(Long id) {
		String hql="FROM LeaveBill o where o.id=?";
		LeaveBill leaveBill=null;
		List<LeaveBill> list = this.getHibernateTemplate().find(hql,id);
		if (list!=null&&list.size()>0) {
			leaveBill=list.get(0);
		}
		
		return leaveBill;
	}

	
}
