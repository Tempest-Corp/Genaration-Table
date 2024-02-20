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
import com.main.models.People;

@RestController
@RequestMapping("/people")
public class PeopleController  {

	@Autowired
	private com.main.repository.PeopleRepository peopleRepository;


	@DeleteMapping("/{id}")
	public Status delete(Long id){
	    peopleRepository.deleteById(id);
	    return null; 
	}

	@PostMapping
	public Status add(@RequestBody People people){
	    People res =peopleRepository.save(people);
	    return new Status("ok", "People inserted succesfully",res); 
	}

	@GetMapping
	public Status getAll(){
	    List<People> res =peopleRepository.findAll();
	    return new Status("ok", null,res); 
	}

	@PutMapping
	public Status update(@RequestBody People people){
	    People res =peopleRepository.save(people);
	    return new Status("ok", "People updated succesfully",res); 
	}

	@ExceptionHandler(Exception.class)
	public Status handleException(Exception e){
    	e.printStackTrace();
    	return new Status("error", e.getMessage(),null);
	}


}
