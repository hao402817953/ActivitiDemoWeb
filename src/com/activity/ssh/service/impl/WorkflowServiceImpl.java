package com.activity.ssh.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.explorer.util.StringUtil;
import org.apache.commons.lang.StringUtils;

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

	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData taskFromData = formService.getTaskFormData(taskId);
		//获取From key的值
		String url=taskFromData.getFormKey();
		System.out.println("=========================--------------------------");
		
		return url;
	}

	@Override
	public LeaveBill findLeaveBillByTaskId(String taskId) {
		//1：使用任务ID，查询任务对象Task
				Task task = taskService.createTaskQuery()//
								.taskId(taskId)//使用任务ID查询
								.singleResult();
				//2：使用任务对象Task获取流程实例ID
				String processInstanceId = task.getProcessInstanceId();
				//3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
				ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
								.processInstanceId(processInstanceId)//使用流程实例ID查询
								.singleResult();
				//4：使用流程实例对象获取BUSINESS_KEY
				String buniness_key = pi.getBusinessKey();
				//5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
				String id = "";
				if(StringUtils.isNotBlank(buniness_key)){
					//截取字符串，取buniness_key小数点的第2个值
					id = buniness_key.split("\\.")[1];
				}
				//查询请假单对象
				//使用hql语句：from LeaveBill o where o.id=1
				LeaveBill leaveBill = leaveBillDao.findOneLeaveBill(Long.parseLong(id));
				return leaveBill;
	}

	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
				List<String> list = new ArrayList<String>();
				//1:使用任务ID，查询任务对象
				Task task = taskService.createTaskQuery()//
							.taskId(taskId)//使用任务ID查询
							.singleResult();
				//2：获取流程定义ID
				String processDefinitionId = task.getProcessDefinitionId();
				//3：查询ProcessDefinitionEntiy对象
				ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
				//使用任务对象Task获取流程实例ID
				String processInstanceId = task.getProcessInstanceId();
				//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
				ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
							.processInstanceId(processInstanceId)//使用流程实例ID查询
							.singleResult();
				//获取当前活动的id
				String activityId = pi.getActivityId();
				//4：获取当前的活动
				ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
				//5：获取当前活动完成之后连线的名称
				List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
				if(pvmList!=null && pvmList.size()>0){
					for(PvmTransition pvm:pvmList){
						String name = (String) pvm.getProperty("name");
						if(StringUtils.isNotBlank(name)){
							list.add(name);
						}
						else{
							list.add("默认提交");
						}
					}
				}
				return list;
	}

}
