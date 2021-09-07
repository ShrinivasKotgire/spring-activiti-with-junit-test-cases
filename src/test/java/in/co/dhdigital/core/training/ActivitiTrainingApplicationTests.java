package in.co.dhdigital.core.training;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import in.co.dhdigital.core.training.services.ActivitiInterface;

@SpringBootTest
@AutoConfigureTestDatabase
class ActivitiTrainingApplicationTests {

	@Autowired
	ActivitiInterface activitiInterface;

	@Test
	void contextLoads() {
	}

	@Test
	public void whenManagerApproves_thenHRAction() {
		String pid = activitiInterface.startNewProcess("leave_process");

		// Check if task is assigned to manager
		List<Task> tasks = activitiInterface.getTasksByUser("manager");
		assertTrue(tasks.stream().anyMatch(t -> t.getProcessInstanceId().equals(pid)), "Task Not found with Manager");

		String taskId = tasks.stream().filter(t -> t.getProcessInstanceId().equals(pid)).findFirst().get().getId();

		// Approve the task
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("decision", "APPROVED");
		activitiInterface.completeTask(taskId, params);

		// Check if task is accessible to HR
		List<Task> hrTasks = activitiInterface.getTasksByGroup("HR_TEAM");
		assertFalse(hrTasks.stream().anyMatch(t -> t.getProcessInstanceId().equals(pid)), "Task Not found with HR Team");
		
		// Signal the receive task to move forward
		activitiInterface.signal(pid, "waitingTask");
		
		// Check if task is accessible to HR
		hrTasks = activitiInterface.getTasksByGroup("HR_TEAM");
		assertTrue(hrTasks.stream().anyMatch(t -> t.getProcessInstanceId().equals(pid)), "Task Not found with HR Team");

		// TODO: Claim task

		// TODO: Check if task is claimed by one of the HR team.

		// TODO: Complete task

		// TODO: Check if process ended.
	}

//	@Test
	public void whenManagerRejects_thenProcessEnd() {
		String pid = activitiInterface.startNewProcess("leave_process");

		// Check if task is assigned to manager
		List<Task> tasks = activitiInterface.getTasksByUser("manager");
		assertTrue(tasks.stream().anyMatch(t -> t.getProcessInstanceId().equals(pid)), "Task Not found with Manager");

		String taskId = tasks.stream().filter(t -> t.getProcessInstanceId().equals(pid)).findFirst().get().getId();

		// Reject the task
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("decision", "REJECT");
		activitiInterface.completeTask(taskId, params);

		// Check if process ended.
		ProcessInstance processInstance = activitiInterface.getProcessInstance(pid);
		assertNull(processInstance, "Process Instance did not complete");
	}

	@Test
	public void whenWithdraw_thenProcessEnd() {
		String pid = activitiInterface.startNewProcess("leave_process");

		// Check if task is assigned to manager
		List<Task> tasks = activitiInterface.getTasksByUser("manager");
		assertTrue(tasks.stream().anyMatch(t -> t.getProcessInstanceId().equals(pid)), "Task Not found with Manager");

		String taskId = tasks.stream().filter(t -> t.getProcessInstanceId().equals(pid)).findFirst().get().getId();

		// Withdraw request
		activitiInterface.signal(pid, "withdrawSignal");

		// Check if process ended.
		ProcessInstance processInstance = activitiInterface.getProcessInstance(pid);
		assertNull(processInstance, "Process Instance did not complete");

	}
}
