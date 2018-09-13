package models.dto;

public class NameDTO{

	private String title;
	private String firstName;
	private String lastName;
	private String middleName;
	private String suffix;


	public NameDTO(){

	}

	public NameDTO(String newFirstName, String newLastName, String newMiddleName,
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
