package id.co.map.mapwebreportapplication.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter  {
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/date-picker/**").addResourceLocations("classpath:/static/date-picker/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/image/**").addResourceLocations("classpath:/static/image/");
		registry.addResourceHandler("/fragment/**").addResourceLocations("classpath:/templates/fragment/");
		registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:/static/vendor/");
		registry.addResourceHandler("/select2/**").addResourceLocations("classpath:/static/select2/");
		registry.addResourceHandler("/sweet_alert2/**").addResourceLocations("classpath:/static/sweet_alert2/");
		registry.addResourceHandler("/datatables.net/**").addResourceLocations("classpath:/static/datatables.net/");
		registry.addResourceHandler("/datatables.net-bs/**").addResourceLocations("classpath:/static/datatables.net-bs/");
		registry.addResourceHandler("/bootstrap/**").addResourceLocations("classpath:/static/bootstrap/");
		registry.addResourceHandler("/font-awesome/**").addResourceLocations("classpath:/static/font-awesome/");
		registry.addResourceHandler("/jquery/**").addResourceLocations("classpath:/static/jquery/");
		
    }
	
}
