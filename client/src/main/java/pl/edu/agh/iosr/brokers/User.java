package pl.edu.agh.iosr.brokers;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	private String password;

	public User(){
	}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public String toString() {
		return "User[name="+name+"]";
	}
}
