package com.main.controllers; 

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.main.utils.Status;
import com.main.models.Car;

@RestController
@RequestMapping("/car")
public class CarController  {

	@Autowired
	private com.main.repository.CarRepository carRepository;


	@DeleteMapping("/{id}")
	public Status delete(Long id){
	    carRepository.deleteById(id);
	    return null; 
	}

	@PostMapping
	public Status add(@RequestBody Car car){
	    Car res =carRepository.save(car);
	    return new Status("ok", "Car inserted succesfully",res); 
	}

	@GetMapping
	public Status getAll(){
	    List<Car> res =carRepository.findAll();
	    return new Status("ok", null,res); 
	}

	@PutMapping
	public Status update(@RequestBody Car car){
	    Car res =carRepository.save(car);
	    return new Status("ok", "Car updated succesfully",res); 
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e){
    	e.printStackTrace();
    	return new Status("error", e.getMessage(),null);
	}


}
