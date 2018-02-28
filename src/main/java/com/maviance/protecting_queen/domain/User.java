package com.maviance.protecting_queen.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is an entity class for the user persistence. The registered users will be able to access the API
 * @author Rostow
 *
 */
@Entity 
@Table(name = "USERS")
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; 
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
		
 
	User() { 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}		
}
