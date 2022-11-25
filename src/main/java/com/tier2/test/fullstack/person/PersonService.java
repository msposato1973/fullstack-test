package com.tier2.test.fullstack.person;

import java.util.List;

import com.tier2.test.fullstack.model.Person;



/**
 * ToDo :: Create an implementation of this interface to read/write to the database
 */
public interface PersonService {

    List<Person> getAll();

    Person getById(final long id);

    List<Person> findByName(final String name);

    Person save(final Person person);
}
