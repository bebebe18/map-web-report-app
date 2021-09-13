package id.co.map.mapwebreportapplication.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {

	private static final Logger logger = LogManager.getLogger(IndexController.class);
	
	@GetMapping({"", "/", "/index"})
	public String getIndex() {	
		logger.info("Report By Channel Page Request");
		return "index";
	}
}
