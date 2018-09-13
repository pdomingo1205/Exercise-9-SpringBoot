package models.projection;

import models.dto.NameDTO;
import java.util.*;

public class PersonGwaDTO{

	 private Long personId;
	 private NameDTO name;
	 private Double GWA;

	public PersonGwaDTO(){

	}


	public Long getPersonId(){
		return personId;
	}

	public NameDTO getName(){
		return name;
	}

	public Double getGWA(){
		return GWA;
	}


	public void setPersonId(Long newID){
		this.personId = newID;
	}

	public void setName(NameDTO newName){
		this.name = newName;
	}

	public void setGWA(Double newGWA){
		this.GWA = newGWA;
	}

	@Override
	public String toString(){
		return(String.format("|%s\t|%s", name));
	}
}
