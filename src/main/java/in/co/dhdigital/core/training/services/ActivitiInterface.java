package in.co.dhdigital.core.training.services;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public interface ActivitiInterface {
	String startNewProcess(String processName);
	
	List<Task> getTasksByUser(String username);
	
	List<Task> getTasksByGroup(String groupName);
	
	void completeTask(String taskId, Map<String, Object> params);
	
	void claimTask(String username);
	
	boolean checkInstanceStatus(String processInstanceId);
	
	ProcessInstance getProcessInstance(String pid);

	void signal(String pid, String activityName);
	
	Task getTask(String taskId);
	

}
