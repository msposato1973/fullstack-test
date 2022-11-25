package com.tier2.test.fullstack.repository;

import java.util.List;


 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tier2.test.fullstack.model.Person;

@Repository
public interface PersonRespository extends JpaRepository<Person, Long> {
	
	@Query(value = "SELECT * FROM person WHERE name = :name", nativeQuery = true)
    List<Person> findByName(@Param("name") String name);
	
	@Query(value = "SELECT * FROM person WHERE id = :id", nativeQuery = true)
    List<Person> findByPersonId(@Param("id") Long id);
}
