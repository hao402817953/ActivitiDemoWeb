package com.activity.ssh.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import com.activity.ssh.web.form.WorkflowBean;

public interface IWorkflowService {
	/**
	 * 部署流程定义
	 * @title saveNameDeplove
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @param file
	 * @param fileName
	 */
	public void saveNameDeplove(File file, String fileName);
	/**
	 * 查询部署对象信息,对应表(act_re_deployment)
	 * @title findDeploymentList
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @return
	 */
	public List<Deployment> findDeploymentList();
	/**
	 * 查询流程定义
	 * @title findProcessDefinitionList
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @return
	 */
	public List<ProcessDefinition> findProcessDefinitionList();
	/**
	 * 使用部署对象ID查看流程图
	 * @title findImageStream
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @param deploymentId
	 * @param imageName
	 * @return
	 */
	public InputStream findImageStream(String deploymentId, String imageName);
	/**
	 * 
	 * @title deleteProcessDefinitionByDeploymentId
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月16日
	 * @param deploymentId
	 */
	public void deleteProcessDefinitionByDeploymentId(String deploymentId);
	/**
	 * 启动流程实例
	 * @title updateStartProcess
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月17日
	 * @param workflowBean
	 */
	public void updateStartProcess(WorkflowBean workflowBean);
	/**
	 * 查询当前用的任务
	 * @title findTaskList
	 * @description TODO
	 * @author Administrator
	 * @create_date 2017年2月17日
	 * @param name
	 * @return
	 */
	public List<Task> findTaskList(String name);

	

}
