package models.dto;

import java.util.Set;
import java.util.HashSet;
import models.dto.PersonDTO;

public class RoleDTO{

	private Long roleId;
	private String role;

	public RoleDTO(){

	}

	public RoleDTO(String newRole){
		this.role = newRole;
	}

	public RoleDTO(Long newRoleId, String newRole){
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
