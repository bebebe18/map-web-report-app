package id.co.map.mapwebreportapplication.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import id.co.map.mapwebreportapplication.model.Company;
import id.co.map.mapwebreportapplication.model.ReturnDetailsByChannel;
import id.co.map.mapwebreportapplication.model.ReturnsByChannel;
import id.co.map.mapwebreportapplication.service.CommonService;
import id.co.map.mapwebreportapplication.service.ReturnDetailsByChannelDaoService;
import id.co.map.mapwebreportapplication.service.ReturnsByChannelDaoService;
import id.co.map.mapwebreportapplication.utility.MyDateConverter;

@Controller
public class ReturnsByChannelController {
	
	private static final Logger logger = LogManager.getLogger(ReturnsByChannelController.class);
	
	@Autowired 
	private ReturnsByChannelDaoService returnsService;
	
	@Autowired 
	private ReturnDetailsByChannelDaoService detailsService;
	
	@Autowired
	private CommonService commonService;
	
	private MyDateConverter myDateConverter;
	
	public ReturnsByChannelController() {
		myDateConverter = new MyDateConverter();
	}
	
	@GetMapping("/ReturnsByChannel")
	public String index(Model m) {
		logger.info("Report By Channel Page Request");
		List<String> channels = commonService.getChannels();
		List<Company> companies = commonService.getCompanies();

		m.addAttribute("channels", channels);
		m.addAttribute("companies", companies);
		return "returnsByChannel";
	}
	
	@GetMapping("/ReturnsByChannel/report/ReturnsByChannel")
	public ModelAndView genRptTransactionByChannel(ModelAndView m,
			@RequestParam(value ="format", required = false) String format,
			@RequestParam(value ="startDate", required = true) Integer startDate,
			@RequestParam(value ="endDate", required = true) Integer endDate,
			@RequestParam(value ="channel", required = false) String channel,
			@RequestParam(value ="company", required = true) String company,
			@RequestParam(value = "warehouse", required = false) String warehouse) {

		logger.info("Start Generate Return Report");

		format = format == null ? "pdf": format;		

		Iterable<ReturnsByChannel> data = returnsService.findRetrunsByChannel(startDate, endDate, channel, company, warehouse);
		m.addObject("dataSource", data);
		m.addObject("format", format);
		m.addObject("startDate",myDateConverter.convertDate(startDate));
		m.addObject("endDate", myDateConverter.convertDate(endDate));
		m.addObject("generateDate", myDateConverter.getCurrentDateTime());
		m.setViewName("report_ReturnsByChannel");
		return m;
	}
	
	@GetMapping("/ReturnsByChannel/report/ReturnDetailByChannel")
	public String genRptTransactionDetailByChannel(Model m,
			@RequestParam(value = "format", required = false) String format,
			@RequestParam(value = "startDate", required = true) Integer startDate,
			@RequestParam(value = "endDate", required = true) Integer endDate,
			@RequestParam(value ="channel", required = false) String channel,
			@RequestParam(value = "company", required = true) String company,
			@RequestParam(value = "warehous", required = false) String warehouse) {
		
		logger.info("Start Generate Return Detial Request");
		
		format = format == null ? "pdf": format;
		
		Iterable<ReturnDetailsByChannel> data = detailsService.findReturnDetailsByChannel(startDate, endDate, channel, company, warehouse);
		m.addAttribute("dataSource", data);
		m.addAttribute("format", format);
		m.addAttribute("startDate",myDateConverter.convertDate(startDate));
		m.addAttribute("endDate", myDateConverter.convertDate(endDate));
		m.addAttribute("generateDateTime", myDateConverter.getCurrentDateTime());
		return "report_ReturnDetailsByChannel";
	}
	
}
