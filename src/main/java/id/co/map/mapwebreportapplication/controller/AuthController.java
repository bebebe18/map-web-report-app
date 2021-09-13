package id.co.map.mapwebreportapplication.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class AuthController {
	
	private static final Logger logger = LogManager.getLogger(AuthController.class);
	
    @GetMapping("/auth/login")
    public String showLogin(){
    	return "login";
    }



}
