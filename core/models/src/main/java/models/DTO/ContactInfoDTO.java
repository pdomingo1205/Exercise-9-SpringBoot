package models.dto;

public class ContactInfoDTO{

	private Long contactInfoId;
	private String contactInfo;
	private String contactType;


	public ContactInfoDTO(){
	}

	public ContactInfoDTO(String newContactInfo){
		this.contactInfo = newContactInfo;
	}

	public ContactInfoDTO(String newContactInfo, String newContactType){
		this.contactInfo = newContactInfo;
		this.contactType = newContactType;
	}


	public Long getContactInfoId(){
		return contactInfoId;
	}

	public String getContactInfo(){
		return contactInfo;
	}

	public String getContactType(){
		return contactType;
	}

	public void setContactInfoId(Long newContactInfoId){
		this.contactInfoId = newContactInfoId;
	}

	public void setContactInfo(String newContactInfo){
		this.contactInfo = newContactInfo;
	}

	public void setContactType(String newContactType){
		this.contactType = newContactType;
	}

	@Override
	public String toString(){
		return String.format("ID:%d | %s: %s", contactInfoId, contactType, contactInfo);
	}

}
