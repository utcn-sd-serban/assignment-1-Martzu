package com.example.assig1.persistence.memory;

import com.example.assig1.persistence.api.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory  implements RepositoryFactory {

    //instance for each repo for evrything
    private final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();
    private final InMemoryQuestionTagRepository questionTagRepository = new InMemoryQuestionTagRepository();
    private final InMemoryTagRepository tagRepository = new InMemoryTagRepository();
    private final InMemoryAnswerRepository answerRepository = new InMemoryAnswerRepository();
    private final InMemoryVoteAnswerRepository voteAnswerRepository = new InMemoryVoteAnswerRepository();
    private final InMemoryVoteQuestionRepository voteQuestionRepository = new InMemoryVoteQuestionRepository();


    @Override
    public QuestionRepository createQuestionRepository() {
        return questionRepository;
    }

    @Override
    public UserRepository createUserRepository() {
        return userRepository;
    }

    @Override
    public TagRepository createTagRepository() {
        return tagRepository;
    }

    @Override
    public QuestionTagRepository createQuestionTagRepository() {
        return questionTagRepository;
    }

    @Override
    public AnswerRepository createAnswerRepository() {
        return answerRepository;
    }

    @Override
    public VoteAnswerRepository createVoteAnswerRepository() {
        return voteAnswerRepository;
    }

    public VoteQuestionRepository createVoteQuestionRepository() {
        return voteQuestionRepository;
    }



}
