package com.example.assig1.persistence.jdbc;


import com.example.assig1.persistence.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "JDBC")
public class JdbcRepositoryFactory implements RepositoryFactory {

    private final JdbcTemplate template;

    @Override
    public QuestionRepository createQuestionRepository() {
        return new JdbcQuestionRepository(template);
    }

    @Override
    public UserRepository createUserRepository() {
        return new JdbcUserRepository(template);
    }

    @Override
    public TagRepository createTagRepository() {
        return new JdbcTagRepository(template);
    }

    @Override
    public QuestionTagRepository createQuestionTagRepository() {
        return new JdbcQuestionTagRepository(template);
    }

    public AnswerRepository createAnswerRepository()
    {
        return new JdbcAnswerRepository(template);
    }

    @Override
    public VoteAnswerRepository createVoteAnswerRepository() {
        return new JdbcVoteAnswerRepository(template);
    }

    public VoteQuestionRepository createVoteQuestionRepository()
    {
        return new JdbcVoteQuestionRepository(template);
    }
}
