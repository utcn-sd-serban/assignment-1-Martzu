package com.example.assig1.seed;

import com.example.assig1.model.User;
import com.example.assig1.persistence.api.RepositoryFactory;
import com.example.assig1.persistence.api.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
// The Order ensures that this command line runner is ran first (before the ConsoleController)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserSeed implements CommandLineRunner {
    private final RepositoryFactory factory;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        UserRepository repository = factory.createUserRepository();
        if (repository.findAll().isEmpty()) {
            repository.save(new User("Alex M", "salam", "A.B@example.com"));
            repository.save(new User("C", "D", "C.D@example.com"));
            repository.save(new User("E", "F", "E.F@example.com"));
        }
    }
}
