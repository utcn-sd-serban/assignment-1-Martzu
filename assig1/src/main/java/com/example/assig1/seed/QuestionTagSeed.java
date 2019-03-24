package com.example.assig1.seed;

import com.example.assig1.model.QuestionTag;
import com.example.assig1.model.Tag;
import com.example.assig1.model.User;
import com.example.assig1.persistence.api.QuestionTagRepository;
import com.example.assig1.persistence.api.RepositoryFactory;
import com.example.assig1.persistence.api.TagRepository;
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
public class QuestionTagSeed implements CommandLineRunner {
    private final RepositoryFactory factory;

    @Override
    @Transactional
    public void run(String... args) throws Exception
    {
        QuestionTagRepository repository = factory.createQuestionTagRepository();
        if (repository.findAll().isEmpty())
        {
            repository.save(new QuestionTag(1,1));
            repository.save(new QuestionTag(1,2));
            repository.save(new QuestionTag(2,1));
            repository.save(new QuestionTag(3,3));


        }
    }
}
