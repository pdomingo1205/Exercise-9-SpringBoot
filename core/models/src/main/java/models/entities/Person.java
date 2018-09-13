package models.entities;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;


import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "person")
public class Person{

	@Id
    @GeneratedValue(generator = "person_generator")
    @SequenceGenerator(
            name = "person_generator",
            sequenceName = "person_sequence",
            initialValue = 1
    )
	private Long id;

	@Embedded
	private Name name;

	@Embedded
	private Address address;

	private Double GWA;

	@Temporal(TemporalType.DATE)
    @Column(name = "birth_day")
	private Date bDay;

	@Temporal(TemporalType.DATE)
    @Column(name = "date_hired")
	private Date dateHired;

	@Column(name = "curr_employed")
	private Boolean currEmployed;

	@OneToMany(mappedBy="person", orphanRemoval=true)
    @Cascade({CascadeType.ALL})
    @Fetch(FetchMode.SELECT)
	private Set<ContactInfo> contactInfo = new HashSet<ContactInfo>(0);

    @ManyToMany(fetch=FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
	@JsonIgnore
    @JoinTable(name = "person_roles",
        joinColumns = { @JoinColumn(name = "person_id")},
        inverseJoinColumns = { @JoinColumn(name = "role_id")})
	private Set<Role> roles = new HashSet<Role>(0);

	public Person(){

	}

	public Person(Name newName, Address newAddress, Date newBDay, Double newGWA,
					Date newDateHired,
					Boolean newCurrEmployed){

		this.name = newName;
		this.address = newAddress;
		this.bDay = newBDay;
		this.GWA = newGWA;
		this.dateHired = newDateHired;
		this.currEmployed = newCurrEmployed;

	}

	public Long getId(){
		return id;
	}

	public Name getName(){
		return name;
	}

	public Address getAddress(){
		return address;
	}

	public Date getbDay(){
		return bDay;
	}

	public Double getGWA(){
		return GWA;
	}

	public Date getDateHired() {
		return dateHired;
	}

	public Boolean getCurrEmployed(){
		return currEmployed;
	}

	public Set<Role> getRoles(){
		return this.roles;
	}

	public void setId(Long newID){
		this.id = newID;
	}

	public void setName(Name newName){
		this.name = newName;
	}

	public void setAddress(Address newAddress){
		this.address = newAddress;
	}

	public void setbDay(Date newBDay){
		this.bDay = newBDay;
	}

	public void setGWA(Double newGWA){
		this.GWA = newGWA;
	}

	public void setDateHired(Date newDateHired){
		this.dateHired = newDateHired;
	}

	public void setCurrEmployed(Boolean newEmploymentStatus){
		this.currEmployed = newEmploymentStatus;
	}

	public void setRoles(Set<Role> newRoles){
		this.roles = newRoles;
	}

	@Override
	public String toString(){
		return(String.format("|%s\t|%s\t|%s\t|%f\t|%s\t|", name, address, bDay, GWA, dateHired));
	}
}
