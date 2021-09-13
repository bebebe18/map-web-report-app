package id.co.map.mapwebreportapplication.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import id.co.map.mapwebreportapplication.service.UserDetailsServiceImpl;
import id.co.map.mapwebreportapplication.utility.custAuthProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private custAuthProvider authProvider;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
    	
    	http.csrf().disable();
    	
    	 // The pages or resources do not require login
        http.authorizeRequests().antMatchers(
                "/auth/login",
                "/logout",
                "/bootstrap/**",
                "/font-awesome/**",
                "/css/**",
                "/image/**",
                "/plugins/**",
                "/internet_explorer/**",
                "/jquery/**"
                ).permitAll();
    	
    	http.authorizeRequests().anyRequest().authenticated()
    		.and()
	    		.formLogin()
	    		.loginProcessingUrl("/j_spring_security_check")
		        .loginPage("/auth/login")
		        .defaultSuccessUrl("/")
		        .failureUrl("/auth/login?error=true")
		        .usernameParameter("username")
		        .passwordParameter("password")
	        .and()
	        	.logout().logoutUrl("/logout")
	        	.logoutSuccessUrl("/auth/login");
    }
    
    @Bean 
    public BCryptPasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }

}


