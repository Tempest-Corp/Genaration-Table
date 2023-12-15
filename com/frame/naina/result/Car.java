package com.frame.naina.result ;

public class Car {

	 Integer id;
	 String imma;
	 String color;
	 Double kilometrage;

	public Car(Integer id, String imma, String color, Double kilometrage){
		this.id = id;
		this.imma = imma;
		this.color = color;
		this.kilometrage = kilometrage;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImma(String imma) {
		this.imma = imma;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setKilometrage(Double kilometrage) {
		this.kilometrage = kilometrage;
	}

	public Integer getId() {
		return this.id;
	}

	public String getImma() {
		return this.imma;
	}

	public String getColor() {
		return this.color;
	}

	public Double getKilometrage() {
		return this.kilometrage;
	}

} 