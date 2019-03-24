package com.example.assig1.persistence.jdbc;

import com.example.assig1.model.User;
import com.example.assig1.persistence.api.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
	// The Jdbc template is a helper class for doing JDBC operations (usually "templates" simplify common tasks)
	// check out https://spring.io/guides/gs/relational-data-access/
	private final JdbcTemplate template;

	@Override
	public List<User> findAll() {
		return template.query("SELECT * FROM stackuser", new UserMapper());
	}

	@Override
	public User save(User user) {
		if(user.getId() == 0)
		{
			int id = insert(user);
			user.setId(id);
		}
		else
		{
			update(user);
		}
		return user;
	}


	@Override
	public void remove(User user) {
		template.update("DELETE FROM stackuser WHERE id = ?", user.getId());
	}

	@Override
	public Optional<User> findById(int id) {
		List<User> users = template.query("SELECT * FROM stackuser WHERE id = ?", new UserMapper(), id);
		return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
	}

	@Override
	public boolean verifyUser(String name, String password) {
		return findAll().stream().filter((User user) -> user.getFullName().equals(name) && user.getPassword().equals(password)).count() == 1;
	}

	@Override
	public Optional<User> findByName(String name) {
		return findAll().stream().filter((User user) -> user.getFullName().equals(name)).findFirst();
	}

	private int insert(User user) {
		// we use the SimpleJdbcInsert to easily retrieve the generated ID
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		insert.setTableName("stackuser");
		insert.usingGeneratedKeyColumns("id");
		Map<String, Object> map = new HashMap<>();
		map.put("full_name", user.getFullName());
		map.put("password", user.getPassword());
		map.put("email", user.getEmail());
		return insert.executeAndReturnKey(map).intValue();
	}

	private void update(User user) {
		template.update("UPDATE stackuser SET full_name = ?, password = ?, email = ? WHERE id = ?",
				user.getFullName(), user.getPassword(), user.getEmail(), user.getId());
	}

}
