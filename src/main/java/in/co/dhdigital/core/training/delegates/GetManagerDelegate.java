package in.co.dhdigital.core.training.delegates;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.co.dhdigital.core.training.services.impl.UtilityService;

@Component("GetManagerDelegate")
public class GetManagerDelegate implements JavaDelegate{

	@Autowired
	UtilityService utilityService;
	
	@Override
	public void execute(DelegateExecution execution) {
		
		execution.setVariable("manager_username", utilityService.getManagerUsername());
	}

	
}
