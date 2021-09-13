package id.co.map.mapwebreportapplication.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ReportExceptionHandler {
	
	@ExceptionHandler(DataNotFoundException.class)
	public ModelAndView handleError404(
			HttpServletRequest servlet, 
			DataNotFoundException ex) {
		ModelAndView m = new ModelAndView();
		m.addObject("message", ex.getMessage());
		m.setViewName("/error/404");
		return m;
	}
	
}
