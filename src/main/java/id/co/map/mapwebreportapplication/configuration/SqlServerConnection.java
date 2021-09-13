package id.co.map.mapwebreportapplication.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SqlServerConnection {
	@Value("${spring.datasource.sqlserver.ETP_EAS_LIVE.url}")
	private String connection_url;
	
	@Value("${spring.datasource.sqlserver.ETP_EAS_LIVE.username}")
	private String username;
	
	@Value("${spring.datasource.sqlserver.ETP_EAS_LIVE.password}")
	private String password;
	
	@Value("${spring.datasource.sqlserver.Login.url}")
	private String log_connection_url;
	
	@Value("${spring.datasource.sqlserver.Login.username}")
	private String log_username;
	
	@Value("${spring.datasource.sqlserver.Login.password}")
	private String log_password;
	
	@Value("${spring.datasource.sqlserver.ETP_EAS_LIVE.driverClassName}")
	private String driverClassName;
	
	public SqlServerConnection() {
		this.connection_url = connection_url;
		this.username = username;
		this.password = password;
		this.log_connection_url = log_connection_url;
		this.log_username = log_username;
		this.log_password = log_password;
		this.driverClassName = driverClassName;
	}
	
	public String getConnection_url() {
		return connection_url;
	}

	public void setConnection_url(String connection_url) {
		this.connection_url = connection_url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getLog_connection_url() {
		return log_connection_url;
	}

	public void setLog_connection_url(String log_connection_url) {
		this.log_connection_url = log_connection_url;
	}

	public String getLog_username() {
		return log_username;
	}

	public void setLog_username(String log_username) {
		this.log_username = log_username;
	}

	public String getLog_password() {
		return log_password;
	}

	public void setLog_password(String log_password) {
		this.log_password = log_password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
}
