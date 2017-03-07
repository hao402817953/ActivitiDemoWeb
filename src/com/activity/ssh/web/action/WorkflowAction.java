package com.activity.ssh.web.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.websocket.Session;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.struts2.ServletActionContext;

import com.activity.ssh.domain.LeaveBill;
import com.activity.ssh.service.ILeaveBillService;
import com.activity.ssh.service.IWorkflowService;
import com.activity.ssh.utils.SessionContext;
import com.activity.ssh.utils.ValueContext;
import com.activity.ssh.web.form.WorkflowBean;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import freemarker.core.Comment;

@SuppressWarnings("serial")
public class WorkflowAction extends ActionSupport implements ModelDriven<WorkflowBean> {

	private WorkflowBean workflowBean = new WorkflowBean();
	
	@Override
	public WorkflowBean getModel() {
		return workflowBean;
	}
	
	private IWorkflowService workflowService;
	
	private ILeaveBillService leaveBillService;

	public void setLeaveBillService(ILeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	public void setWorkflowService(IWorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	/**
	 * 部署管理首页显示
	 * @return
	 */
	public String deployHome(){
		//查询部署对象信息,对应表(act_re_deployment)
		List<Deployment> depList=workflowService.findDeploymentList();
		//查询流程定义,对应表(act_re_procdef)
		List<ProcessDefinition> pdList=workflowService.findProcessDefinitionList();
		//放置到山下文
		ValueContext.putValueContext("depList",depList );
		ValueContext.putValueContext("pdList",pdList );
		return "deployHome";
	}
	
	/**
	 * 发布流程
	 * @return
	 */
	public String newdeploy(){
		File file=workflowBean.getFile();
		String fileName=workflowBean.getFilename();
		workflowService.saveNameDeplove(file,fileName);
		return "list";
	}
	
	/**
	 * 删除部署信息
	 */
	public String delDeployment(){
		String deploymentId = workflowBean.getDeploymentId();
		//删除部署信息
		workflowService.deleteProcessDefinitionByDeploymentId(deploymentId);
		return "list";
	}
	
	/**
	 * 查看流程图
	 */
	public String viewImage(){
		//直接将图写到页面,输出流
		//获取页面传递的值
		String deploymentId = workflowBean.getDeploymentId();
		String imageName = workflowBean.getImageName();
		InputStream in=workflowService.findImageStream(deploymentId,imageName);
		//response获取输出流
		try {
			ServletOutputStream os = ServletActionContext.getResponse().getOutputStream();
			for (int bit = -1; (bit=in.read())!=-1;) {
				os.write(bit);
			}
			os.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// 启动流程
	public String startProcess(){
		workflowService.updateStartProcess(workflowBean);
		
		
		
		
		return "listTask";
	}
	
	
	
	/**
	 * 任务管理首页显示
	 * @return
	 */
	public String listTask(){
		String name=SessionContext.get().getName();
		List<Task> list=workflowService.findTaskList(name);
		ValueContext.putValueContext("list", list);
		return "task";
	}
	
	/**
	 * 打开任务表单
	 */
	public String viewTaskForm(){
		//任务ID
		String taskId=workflowBean.getTaskId();
		String url=workflowService.findTaskFormKeyByTaskId(taskId);
		url +="?taskId="+taskId;
		ValueContext.putValueContext("url", url);
		
		return "viewTaskForm";
	}
	// 准备表单数据
	public String audit(){
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
		LeaveBill leaveBill = workflowService.findLeaveBillByTaskId(taskId);
		ValueContext.putValueStack(leaveBill);
		/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
		List<String> outcomeList = workflowService.findOutComeListByTaskId(taskId);
		ValueContext.putValueContext("outcomeList", outcomeList);
		/**三,*/
		List<Comment> commentList=null;
		ValueContext.putValueContext("commentList", commentList);
		
		
		return "taskForm";
	}
	
	/**
	 * 提交任务
	 */
	public String submitTask(){
		
		
		
		
		
		
		
		
		
		
		
		return "listTask";
	}
	
	/**
	 * 查看当前流程图（查看当前活动节点，并使用红色的框标注）
	 */
	public String viewCurrentImage(){
		return "image";
	}
	
	// 查看历史的批注信息
	public String viewHisComment(){
		return "viewHisComment";
	}
}
