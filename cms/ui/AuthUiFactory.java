/*package com.epam.cms.ui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthUiFactory {
	
	AuthUi authUi;
	@Autowired 
	LoginUi loginUi;
	@Autowired
	RegisterUi registerUi;
	
	public AuthUi getAuthUi(int authUiId){
		
		if(authUiId == 1){
			authUi =  loginUi;
		}
		else if(authUiId == 2) {
			authUi = registerUi;
		}
		else {
			System.out.println("Please Select Valid Option");
		}
		
		return authUi;
	}
	
}
*/