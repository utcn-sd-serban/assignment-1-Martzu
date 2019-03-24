package com.example.assig1.persistence.api;

import com.example.assig1.model.User;


import java.util.List;
import java.util.Optional;

public interface  UserRepository {
	List<User> findAll();
	User save(User user);
	void remove(User user);
	Optional<User> findById(int id);

	boolean verifyUser(String name, String password);

	Optional<User> findByName(String name);
}
