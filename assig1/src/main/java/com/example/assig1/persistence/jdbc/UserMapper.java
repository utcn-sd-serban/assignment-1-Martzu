package com.example.assig1.persistence.jdbc;

import com.example.assig1.model.User;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new User(rs.getInt("id"),
				rs.getString("full_name"),
				rs.getString("password"),
				rs.getString("email"));
	}

}
