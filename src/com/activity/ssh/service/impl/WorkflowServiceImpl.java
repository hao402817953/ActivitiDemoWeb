package com.activity.ssh.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.websocket.Session;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import com.activity.ssh.dao.ILeaveBillDao;
import com.activity.ssh.domain.LeaveBill;
import com.activity.ssh.service.IWorkflowService;
import com.activity.ssh.utils.SessionContext;
import com.activity.ssh.web.form.WorkflowBean;

public class WorkflowServiceImpl implements IWorkflowService {
	/**请假申请Dao*/
	private ILeaveBillDao leaveBillDao;
	
	private RepositoryService repositoryService;
	
	private RuntimeService runtimeService;
	
	private TaskService taskService;
	
	private FormService formService;
	
	private HistoryService historyService;
	
	public void setLeaveBillDao(ILeaveBillDao leaveBillDao) {
		this.leaveBillDao = leaveBillDao;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Override
	public void saveNameDeplove(File file, String fileName) {
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		repositoryService.createDeployment()//创建流程定义
			.name(fileName)
			.addZipInputStream(zipInputStream)
			.deploy();
	}

	@Override
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()//创建部署对象查询
						.orderByDeploymenTime().asc()
						.list();
						
						
		return list;
	}

	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
						.orderByProcessDefinitionVersion().asc()
						.list();
		return list;
	}

	@Override
	public InputStream findImageStream(String deploymentId, String imageName) {
		
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}
	
	@Override
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		
		repositoryService.deleteDeployment(deploymentId);
	}

	@Override
	public void updateStartProcess(WorkflowBean workflowBean) {
		Long id=workflowBean.getId();
		LeaveBill leaveBill = leaveBillDao.findOneLeaveBill(id);
		leaveBill.setState(1);
		leaveBillDao.updateLeaveBill(leaveBill);
		String key = leaveBill.getClass().getSimpleName();
		Map<String, Object> variables=new HashMap<String,Object>();
		variables.put("inputUser", SessionContext.get().getName());
		
		String objId=key+"."+id;
		variables.put("objId", objId);
		runtimeService.startProcessInstanceByKey(key,objId,variables);
 	}

	@Override
	public List<Task> findTaskList(String name) {
		List<Task> list = taskService.createTaskQuery()
					.taskAssignee(name)//个人任务
					.orderByTaskCreateTime().asc().list();
		return list;
	}

}
