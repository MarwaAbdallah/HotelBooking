package model;

public  class User implements Comparable<User>{

	private String email;
	private int id;
	private boolean enabled;
	private String role;
	private String password;
	
	public User(int id, String email) {
		this.id = id;
		this.email = email;
	}
	public User(int id, String email, boolean enabled) {
		this.id = id;
		this.email = email;
		this.enabled = enabled;
	}
	public User(int id, String email, boolean enabled, String role) {
		this.id = id;
		this.email = email;
		this.enabled = enabled;
		this.setRole(role);
	}
	public User(String email, String password, String role) { 
		this.email = email;
		this.enabled = true;
		this.password=password;
		this.setRole(role);
	}
	public User(String userEmail) {
		// TODO Auto-generated constructor stub
		this.email = email;
	}
	public User(String email, String role, boolean enabled) {
		// TODO Auto-generated constructor stub
		this.email = email;
		this.enabled = enabled;
		this.setRole(role);
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public int compareTo(User usrB) {
		// TODO Auto-generated method stub
		return this.getEmail().compareTo(usrB.getEmail());
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
