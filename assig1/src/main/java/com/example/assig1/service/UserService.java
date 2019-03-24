package com.example.assig1.service;


import com.example.assig1.model.User;
import com.example.assig1.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public User saveUser(User user)
    {
        return repositoryFactory.createUserRepository().save(user);
    }

    @Transactional
    public Optional<User> findById(int id)
    {
        return repositoryFactory.createUserRepository().findById(id);
    }

    @Transactional
    public void removeUser(User user)
    {
        repositoryFactory.createUserRepository().remove(user);
    }

    @Transactional
    public List<User> findAll()
    {
        //pt login, ma folosesc de findAll, filtrez care au nmele si parola, si daca countu ii >0, atuncea exista useru
        //TODO in console controller LOGIN
        return repositoryFactory.createUserRepository().findAll();
    }

    @Transactional
    public boolean verifyUser(String name, String password)
    {
        return repositoryFactory.createUserRepository().verifyUser(name, password);
    }


    @Transactional
    public Optional<User> findUserByName(String name)
    {
        return repositoryFactory.createUserRepository().findByName(name);
    }

}
