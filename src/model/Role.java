package model;
enum RoleName {
	Admin, HotelAgent, Customer
}

public class Role {
	private RoleName roleName;
	private String role;
	public Role() {}
	public Role(String rolename) {
		this.setRoleName(RoleName.valueOf(rolename));
		this.setRole(rolename);
	}
	public Role(RoleName roleName) {

		this.setRoleName(roleName);
	}

	public RoleName getRoleName() {
		return roleName;
	}
	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

	
	
}
