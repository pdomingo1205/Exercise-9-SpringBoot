package models.entities;

import java.util.*;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "role")
public class Role{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;

	private String role;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	@JsonIgnore
	private Set<Person> persons = new HashSet<>();

	public Role(){

	}

	public Role(String newRole){
		this.role = newRole;
	}

	public Long getRoleId(){
		return roleId;
	}

	public String getRole(){
		return role;
	}

	public Set<Person> getPersons(){
		return persons;
	}

	public void setPersons(Set<Person> newPersons){
		this.persons = newPersons;
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
