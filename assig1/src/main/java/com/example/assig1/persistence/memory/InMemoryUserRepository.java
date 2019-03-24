package com.example.assig1.persistence.memory;

import com.example.assig1.model.User;
import com.example.assig1.persistence.api.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryUserRepository implements UserRepository {
	// we want to be thread-safe, because this class will be a singleton (just one instance)
	// in order to not use synchronized methods, we use thread-safe classes (ConcurrentHashMap and AtomicInteger)
	private final Map<Integer, User> data = new ConcurrentHashMap<>();
	private final AtomicInteger currentId = new AtomicInteger(0);

	@Override
	public User save(User user) {
		if (user.getId() == 0) {//chestia asta ii dubioasa, tre sa ii dau de la inceput 0 cand se creeaza obiectu si nu ii initializat
			user.setId(currentId.incrementAndGet());
		}
		data.put(user.getId(), user);
		return user;
	}

	@Override
	public void remove(User user) {
		data.remove(user.getId());
	}

	@Override
	public Optional<User> findById(int id) {
		return Optional.ofNullable(data.get(id));
	}

	@Override
	public boolean verifyUser(String name, String password) {
		return findAll().stream().filter((User user)-> user.getFullName().equals(name) && user.getPassword().equals(password)).count() == 1;
	}

	@Override
	public List<User> findAll() {
		return new ArrayList<>(data.values());
	}

	public Optional<User> findByName(String name) {
		return findAll().stream().filter((User user) -> user.getFullName().equals(name)).findFirst();
	}

}
