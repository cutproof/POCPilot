package com.ilinksolutions.uscis.data;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ilinksolutions.uscis.domain.Person;
import com.ilinksolutions.uscis.domain.PersonRowMapper;

// @Repository
public class PGPersistence
{
	NamedParameterJdbcTemplate template;

	public PGPersistence(NamedParameterJdbcTemplate template) {
		this.template = template;
	}
	
	public List<Person> findAll() 
	{
		return template.query("select * from public.person", new PersonRowMapper());
	}

	public void insertEmployee(Person person)
	{
		final String sql = "insert into public.person(person_id, first_name, last_name, contact_no, email) values(:personId,:firstName,:lastName,:contactNumber,:email)";
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource param = new MapSqlParameterSource().addValue("personId", person.getId())
				.addValue("firstName", person.getFirstName())
				.addValue("lastName", person.getLastName())
				.addValue("contactNumber", person.getContactNumber())
				.addValue("email", person.getEmail());
		template.update(sql, param, holder);
	}
}