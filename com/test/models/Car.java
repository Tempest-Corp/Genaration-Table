package com.test.models; 

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "car")
@Table(name = "car")
public class Car  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String imma;

	String color;

	Double kilometrage;

	public Car (Long id, String imma, String color, Double kilometrage){
 		this.id = id;
		this.imma = imma;
		this.color = color;
		this.kilometrage = kilometrage;
 	}

	public Long setId(Long id) {
		this.id = id ;
	}

	public String setImma(String imma) {
		this.imma = imma ;
	}

	public String setColor(String color) {
		this.color = color ;
	}

	public Double setKilometrage(Double kilometrage) {
		this.kilometrage = kilometrage ;
	}

	public Long getId() {
		return  this.id ;
	}

	public String getImma() {
		return  this.imma ;
	}

	public String getColor() {
		return  this.color ;
	}

	public Double getKilometrage() {
		return  this.kilometrage ;
	}

}
