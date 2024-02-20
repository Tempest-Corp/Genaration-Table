package com.main.models; 

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "people")
@Table(name = "people")
public class People  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column
	public String name;

	@Column
	public Boolean ok;

	@Column
	public java.sql.Timestamp dtn;

	public People (Long id, String name, Boolean ok, java.sql.Timestamp dtn){
 		this.id = id;
		this.name = name;
		this.ok = ok;
		this.dtn = dtn;
 	}

	public People (){
  	}

	public void setId(Long id) {
		this.id = id ;
	}

	public void setName(String name) {
		this.name = name ;
	}

	public void setOk(Boolean ok) {
		this.ok = ok ;
	}

	public void setDtn(java.sql.Timestamp dtn) {
		this.dtn = dtn ;
	}

	public Long getId() {
		return this.id ;
	}

	public String getName() {
		return this.name ;
	}

	public Boolean getOk() {
		return this.ok ;
	}

	public java.sql.Timestamp getDtn() {
		return this.dtn ;
	}

}
