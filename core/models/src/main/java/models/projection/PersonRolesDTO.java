package models.projection;


import java.util.*;

public class PersonRolesDTO{

	private Long roleId;
	private String role;

	public PersonRolesDTO(){

	}

	public PersonRolesDTO(String newRole){
		this.role = newRole;
	}

	public PersonRolesDTO(Long newRoleId, String newRole){
		this.roleId = newRoleId;
		this.role = newRole;
	}

	public Long getRoleId(){
		return roleId;
	}

	public String getRole(){
		return role;
	}

	public void setRoleId(Long newRoleId){
		this.roleId = newRoleId;
	}

	public void setRole(String newRole){
		this.role = newRole;
	}

	@Override
	public String toString(){
		return roleId + ": " + role;
	}



}
