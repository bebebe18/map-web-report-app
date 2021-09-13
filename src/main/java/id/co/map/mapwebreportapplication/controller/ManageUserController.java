package id.co.map.mapwebreportapplication.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import id.co.map.mapwebreportapplication.model.Company;
import id.co.map.mapwebreportapplication.model.Role;
import id.co.map.mapwebreportapplication.model.TransactionDetailsByChannel;
import id.co.map.mapwebreportapplication.model.TransactionsByChannel;
import id.co.map.mapwebreportapplication.service.CommonService;
import id.co.map.mapwebreportapplication.service.TransactionDetailsByChannelDaoService;
import id.co.map.mapwebreportapplication.service.TransactionsByChannelDaoService;
import id.co.map.mapwebreportapplication.utility.MyDateConverter;

@Controller
public class ManageUserController {

	private static final Logger logger = LogManager.getLogger(ManageUserController.class);
	
	@Autowired
	private CommonService commonService;
		
	private MyDateConverter myDateConverter;
	
	public ManageUserController() {
		myDateConverter = new MyDateConverter();
	}
	
	@GetMapping("/ManageUser")
	public String index(Model m) {
		List<Company> companies = commonService.getCompanies();
		List<Role> roles = commonService.getRoles();
		
		m.addAttribute("companies", companies);
		m.addAttribute("roles", roles);
		
		return "manageUser";
	}	
	
}