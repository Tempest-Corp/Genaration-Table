package com.test.models; 

@Entity(name = "people")
@Table(name = "people")
public class People  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String name;

	Boolean ok;

	java.sql.Timestamp dtn;

	public People (Long id, String name, Boolean ok, java.sql.Timestamp dtn){
 		this.id = id;
		this.name = name;
		this.ok = ok;
		this.dtn = dtn;
 	}

	public Long setId(Long id) {
		this.id = id ;
	}

	public String setName(String name) {
		this.name = name ;
	}

	public Boolean setOk(Boolean ok) {
		this.ok = ok ;
	}

	public java.sql.Timestamp setDtn(java.sql.Timestamp dtn) {
		this.dtn = dtn ;
	}

	public Long getId() {
		return  this.id ;
	}

	public String getName() {
		return  this.name ;
	}

	public Boolean getOk() {
		return  this.ok ;
	}

	public java.sql.Timestamp getDtn() {
		return  this.dtn ;
	}

}
