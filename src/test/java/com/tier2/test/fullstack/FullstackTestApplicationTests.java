package com.tier2.test.fullstack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FullstackTestApplicationTests {

	@Autowired MockMvc mockMvc;
	@Autowired DataSource dataSource;

	ObjectMapper mapper = new ObjectMapper();

	@Test
	public void whenGetAllPersons_thenReturnAListOf6() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/v1/persons/all"))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();

		List<Map<String, Object>> persons = null;
		try {
			persons = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Map<String, Object>>>() {});
		} catch (Exception e) {
			Assertions.fail("Failed to parse response as a List<Person>");
		}

		Assertions.assertNotNull(persons);
		Assertions.assertEquals(6, persons.size());
	}

	@Test
	public void whenGetPersonById_withValidId_thenReturnSarah() throws Exception {

		mockMvc.perform(get("/api/v1/persons/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("Sarah Michelle Gellar"));
	}

	@Test
	public void whenFindingAPersonByName_thenReturnMatchingResult() throws Exception {

		MvcResult result = mockMvc.perform(get("/api/v1/persons/by-name?name=James Marsters"))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();

		List<Map<String, Object>> persons = null;
		try {
			persons = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Map<String, Object>>>() {});
		} catch (Exception e) {
			Assertions.fail("Failed to parse response as a List<Map<String, Object>>");
		}

		Assertions.assertNotNull(persons);
		Assertions.assertEquals(1, persons.size());
		Assertions.assertEquals("James Marsters", persons.get(0).get("name"));
		Assertions.assertEquals(3, persons.get(0).get("id"));
	}

	@Test
	public void whenSavingAPerson_withoutErrors_thenInsertRecord() throws Exception {
		mockMvc.perform(
				post("/api/v1/persons/save")
					.header("Content-Type", "application/json")
					.content(mapper.writeValueAsString(Map.of("name", "Emma Caulfield", "age", "49",  "created", "2022-11-23T16:42:27.597+00:00")))
			)
			.andDo(print())
			.andExpect(status().isOk());

		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		List<Map<String, Object>> persons = jdbc.queryForList("SELECT id, created, name, age FROM person WHERE name = 'Emma Caulfield';");
		Assertions.assertNotNull(persons);
		Assertions.assertEquals(1, persons.size());

		Map<String, Object> emma = persons.get(0);
		Assertions.assertEquals(7L, emma.get("id"));
		Assertions.assertEquals("Emma Caulfield", emma.get("name"));
		Assertions.assertEquals(49, emma.get("age"));
	}
	
	
	@Test
	public void whenSavingAPerson_withErrors_thenInsertRecord() throws Exception {
		mockMvc.perform(
				post("/api/v1/persons/save")
					.header("Content-Type", "application/json")
					.content(mapper.writeValueAsString(Map.of("name", "Emma Caulfield", "age", "49")))
			)
			.andDo(print())
			.andExpect(status().isOk());

		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		List<Map<String, Object>> persons = jdbc.queryForList("SELECT id, created, name, age FROM person WHERE name = 'Emma Caulfield';");
		Assertions.assertNotNull(persons);
		Assertions.assertEquals(1, persons.size());

		Map<String, Object> emma = persons.get(0);
		Assertions.assertNotEquals(7L, emma.get("id"));
		Assertions.assertNotEquals("Emma Caulfield", emma.get("name"));
		Assertions.assertNotEquals(49, emma.get("age"));
	}

}
