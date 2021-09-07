package in.co.dhdigital.core.training.services.impl;

import org.springframework.stereotype.Service;

@Service
public class UtilityService {
	
	public String getManagerUsername() {
		// Assume that you are getting this from database
		return "manager";
	}
}
