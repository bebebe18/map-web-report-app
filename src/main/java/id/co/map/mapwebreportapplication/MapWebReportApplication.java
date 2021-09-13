package id.co.map.mapwebreportapplication;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class MapWebReportApplication extends SpringBootServletInitializer {

	private static final Logger logger = LogManager.getLogger(MapWebReportApplication.class);
	
	public static void main(String[] args) {
		logger.info("Run Application");
		SpringApplication.run(MapWebReportApplication.class, args);
	}
	
}
