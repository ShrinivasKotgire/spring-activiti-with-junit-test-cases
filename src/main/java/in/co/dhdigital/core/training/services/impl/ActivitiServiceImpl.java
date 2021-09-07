package in.co.dhdigital.core.training.services.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.co.dhdigital.core.training.services.ActivitiInterface;

@Service
public class ActivitiServiceImpl implements ActivitiInterface {

	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;

	/**
	 * Starts a new process insance and returns the id of the newly created process
	 * instance.
	 */
	@Override
	public String startNewProcess(String processName) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processName);
		return processInstance.getId();
	}

	/**
	 * Get all the tasks for the provided user
	 */
	@Override
	public List<Task> getTasksByUser(String username) {
		return taskService.createTaskQuery().taskAssignee(username).list();
	}

	@Override
	public void completeTask(String taskId, Map<String, Object> params) {
		taskService.complete(taskId, params);
	}

	@Override
	public void claimTask(String username) {
		
	}

	@Override
	public boolean checkInstanceStatus(String processInstanceId) {
		return false;
	}

	@Override
	public List<Task> getTasksByGroup(String groupName) {
		return taskService.createTaskQuery().taskCandidateGroup(groupName).list();
	}

	@Override
	public ProcessInstance getProcessInstance(String pid) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
	}

	@Override
	public void signal(String pid, String activityName) {
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(pid).activityId(activityName).singleResult();
		
		runtimeService.trigger(execution.getId());
	}

	@Override
	public Task getTask(String taskId) {
		return taskService.createTaskQuery().taskId(taskId).includeProcessVariables().includeTaskLocalVariables().singleResult();
		
	}

}
