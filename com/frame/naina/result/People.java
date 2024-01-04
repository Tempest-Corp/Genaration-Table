package com.frame.naina.result  ;

public class People {

	 Integer id;
	 String name;
	 Boolean ok;
	 String dtn;

	public People(Integer id, String name, Boolean ok, String dtn){
		this.id = id;
		this.name = name;
		this.ok = ok;
		this.dtn = dtn;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

	public void setDtn(String dtn) {
		this.dtn = dtn;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Boolean getOk() {
		return this.ok;
	}

	public String getDtn() {
		return this.dtn;
	}

} 