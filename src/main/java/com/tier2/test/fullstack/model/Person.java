package com.tier2.test.fullstack.model;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

/**
 * ToDo :: Update this class as a JPA entity so that it matches the following schema:
 * <code>
 *     CREATE TABLE person
 *      (
 *          id      BIGINT       NOT NULL AUTO_INCREMENT,
 *          created DATETIME     NOT NULL,
 *          name    VARCHAR(255) NOT NULL,
 *          age     INT(2)       DEFAULT NULL,
 *
 *          PRIMARY KEY (id)
 *      );
 * </code>
 *
 * ToDo :: Create a matching JPA class in the {@link com.tier2.test.fullstack.repository} package
 */
@Entity
@Table(name = "person")
public class Person {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "created")
	private Date created;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "age")
	private Integer age;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Person() {
		super();
	 
	}
	
	public Person(Long id,  Integer age, Date created, String name) {
		super();
		this.id = id;
		this.created = created;
		this.name = name;
		this.age = age;
	}
	
	public Person(Integer age, Date created, String name) {
		super();
	 	this.created = created;
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", created=" + created + ", name=" + name + ", age=" + age + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(age, created, id, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(age, other.age) && Objects.equals(created, other.created) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
	
	
	
	
}
