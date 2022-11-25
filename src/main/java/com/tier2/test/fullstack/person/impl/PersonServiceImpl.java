package com.tier2.test.fullstack.person.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tier2.test.fullstack.model.Person;
import com.tier2.test.fullstack.person.PersonService;
import com.tier2.test.fullstack.repository.PersonRespository;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRespository personRespository;
	
	@Override
	public List<Person> getAll() {
		
		return personRespository.findAll();
	}

	@Override
	public Person getById(long id) {
		Optional<Person>  optPerson = personRespository.findById(id);
	
		return (optPerson.isPresent()) ? optPerson.get(): new Person() ;
	}

	@Override
	public List<Person> findByName(String name) {
		List<Person> listPerson = personRespository.findByName(name);
		return listPerson;
	}

	@Override
	public Person save(Person person) {
		Person result = personRespository.save(person);
		return result;
	}

}
