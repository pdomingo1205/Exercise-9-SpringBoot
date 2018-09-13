package models.projection;

import models.dto.NameDTO;
import java.util.*;

public class PersonDateHired{

	 private Long personId;
	 private NameDTO name;
	 private Date dateHired;

	public PersonDateHired(){

	}


	public Long getPersonId(){
		return personId;
	}

	public NameDTO getName(){
		return name;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public void setDateHired(Date newDateHired){
		this.dateHired = newDateHired;
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
