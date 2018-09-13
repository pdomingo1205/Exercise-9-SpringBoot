package models.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
public class Name{

	@NotEmpty
	@Column(name = "first_name")
	private String firstName;
	@NotEmpty
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "middle_name")
	private String middleName;

	private String suffix;
	private String title;


	public Name(){

	}

	public Name(String newFirstName, String newLastName, String newMiddleName,
				String newSuffix, String newTitle){

		this.firstName = newFirstName;
		this.lastName = newLastName;
		this.middleName = newMiddleName;
		this.suffix = newSuffix;
		this.title = newTitle;
	}

	public String getTitle(){
		return title;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getMiddleName(){
		return middleName;
	}

	public String getSuffix(){
		return suffix;
	}


	public void setFirstName(String newFirstName){
		this.firstName = newFirstName;
	}

	public void setLastName(String newLastName){
		this.lastName = newLastName;
	}

	public void setMiddleName(String newMiddleName){
		this.middleName = newMiddleName;
	}

	public void setSuffix(String newSuffix){
		this.suffix = newSuffix;
	}

	public void setTitle(String newTitle){
		this.title = newTitle;
	}

	@Override
	public String toString(){
		return String.format("%s %s %s %s %s", this.title, this.firstName, this.middleName, this.lastName, this.suffix);
	}




}
