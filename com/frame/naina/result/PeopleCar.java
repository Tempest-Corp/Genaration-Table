package com.frame.naina.result ;

public class PeopleCar {

	 Integer id;
	 Integer id_people;
	 Integer id_car;
	 String date;

	public PeopleCar(Integer id, Integer id_people, Integer id_car, String date){
		this.id = id;
		this.id_people = id_people;
		this.id_car = id_car;
		this.date = date;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdPeople(Integer id_people) {
		this.id_people = id_people;
	}

	public void setIdCar(Integer id_car) {
		this.id_car = id_car;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getIdPeople() {
		return this.id_people;
	}

	public Integer getIdCar() {
		return this.id_car;
	}

	public String getDate() {
		return this.date;
	}

} 