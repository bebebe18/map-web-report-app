package id.co.map.mapwebreportapplication.model;

import java.util.List;

public class User {
	
	private String userName;
	private String name;
	private Role role;
	private List<Company> companies;
	private String datatimeStamp;
	
	public User() {
		
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	public String getDatatimeStamp() {
		return datatimeStamp;
	}

	public void setDatatimeStamp(String datatimeStamp) {
		this.datatimeStamp = datatimeStamp;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

}
