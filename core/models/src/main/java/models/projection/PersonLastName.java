package models.projection;

import models.dto.NameDTO;
import java.util.*;

public class PersonLastName{

	 private Long personId;
	 private NameDTO name;

	public PersonLastName(){

	}


	public Long getPersonId(){
		return personId;
	}

	public NameDTO getName(){
		return name;
	}


	public void setPersonId(Long newID){
		this.personId = newID;
	}

	public void setName(NameDTO newName){
		this.name = newName;
	}

	@Override
	public String toString(){
		return(String.format("|%s\t|%s", name));
	}
}
