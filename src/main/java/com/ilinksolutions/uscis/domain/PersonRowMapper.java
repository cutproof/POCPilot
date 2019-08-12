package com.ilinksolutions.uscis.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper<Person>
{
	@Override
	public Person mapRow(ResultSet rs, int arg1) throws SQLException
	{
		Person person = new Person();
		person.setId(rs.getInt("person_id"));
		person.setFirstName(rs.getString("first_name"));
		person.setLastName(rs.getString("last_name"));
		person.setContactNumber(rs.getString("contact_no"));
		person.setEmail(rs.getString("email"));
		return person;
	}
}