package com.tier2.test.fullstack.person;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import javax.websocket.server.PathParam;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tier2.test.fullstack.model.Person;
import com.tier2.test.fullstack.person.impl.PersonServiceImpl;

import lombok.RequiredArgsConstructor;

/*
 * ToDo :: Complete this implementation
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PersonController {

	@Autowired
	private final PersonService personService ;
	
	public PersonController(PersonServiceImpl personService) {
		this.personService = personService;
	}

	@GetMapping("/persons/all")
    public List<Person> getAll() {
        return personService.getAll();
    }

	@GetMapping("/persons/{id}")
    public Person getById(@PathVariable("id") final Long id) {

        Person person = personService.getById(id);
        if (person == null) {
            throw new ResponseStatusException(NOT_FOUND, "No person exists with ID " + id);
        }
        return person;
    }

	@GetMapping("/persons/by-name")
    public List<Person> findByName(@RequestParam(name = "name") final String name) {
        return personService.findByName(name);
    }

	@PostMapping("/persons/save")
    public Person create(@RequestBody final Person person) {
    	
    	if (person == null) {
            throw new ResponseStatusException(NOT_FOUND, "No person exists with person " );
        }  
    	
        return personService.save(person);
    }
}
