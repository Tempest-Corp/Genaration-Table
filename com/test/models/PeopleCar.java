package com.test.models; 

@Entity(name = "people_car")
@Table(name = "people_car")
public class PeopleCar  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_people")
	People people;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_car")
	Car car;

	java.sql.Timestamp date;

	public PeopleCar (Long id, People people, Car car, java.sql.Timestamp date){
 		this.id = id;
		this.people = people;
		this.car = car;
		this.date = date;
 	}

	public Long setId(Long id) {
		this.id = id ;
	}

	public People setIdPeople(People id_people) {
		this.id_people = id_people ;
	}

	public Car setIdCar(Car id_car) {
		this.id_car = id_car ;
	}

	public java.sql.Timestamp setDate(java.sql.Timestamp date) {
		this.date = date ;
	}

	public Long getId() {
		return  this.id ;
	}

	public People getIdPeople() {
		return  this.id_people ;
	}

	public Car getIdCar() {
		return  this.id_car ;
	}

	public java.sql.Timestamp getDate() {
		return  this.date ;
	}

}
