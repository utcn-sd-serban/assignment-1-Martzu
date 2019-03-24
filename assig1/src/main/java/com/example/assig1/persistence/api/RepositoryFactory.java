package com.example.assig1.persistence.api;

public interface RepositoryFactory {

    QuestionRepository createQuestionRepository();

    UserRepository createUserRepository();

    TagRepository createTagRepository();

    QuestionTagRepository createQuestionTagRepository();

    AnswerRepository createAnswerRepository();

    VoteAnswerRepository createVoteAnswerRepository();

    VoteQuestionRepository createVoteQuestionRepository();

}
