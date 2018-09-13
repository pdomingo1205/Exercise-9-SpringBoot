package models.entities;

import java.util.*;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "contact_info")
public class ContactInfo{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="contact_info_id")
	private Long contactInfoId;

	@Column(name="contact_info")
	@NotEmpty
	private String contactInfo;

	@Column(name="contact_type")
	@NotEmpty
	private String contactType;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", nullable = false)
	@JsonIgnore
	private Person person;


	public ContactInfo(){
	}

	public ContactInfo(String newContactInfo, Person newPerson){
		this.contactInfo = newContactInfo;
		this.person = newPerson;
	}

	public ContactInfo(String newContactInfo, String newContactType){
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

	public Person getPerson(){
		return person;
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

	public void setPerson(Person newPerson){
		this.person = newPerson;
	}

	@Override
	public String toString(){
		return String.format("ID:%d Person:%s| %s: %s", contactInfoId, person.getName(), contactType, contactInfo);
	}

}
