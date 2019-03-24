package com.example.assig1.service;


import com.example.assig1.model.Answer;
import com.example.assig1.model.Question;
import com.example.assig1.model.User;
import com.example.assig1.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<Answer> listAllAnswers()
    {
        return repositoryFactory.createAnswerRepository().findAll();
    }

    @Transactional
    public List<Answer> listAnswersForQuestion(Question question)
    {
        return repositoryFactory.createAnswerRepository().findAll().stream().filter((Answer answer) -> answer.getQuestionId() == question.getId()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public Answer saveAnswer(Answer answer)
    {
        return repositoryFactory.createAnswerRepository().save(answer);
    }

    @Transactional
    public Optional<Answer> findById(int id)
    {
        return repositoryFactory.createAnswerRepository().findById(id);
    }

    @Transactional
    public List<Answer> listAnswersFromUser(User user)
    {
        return repositoryFactory.createAnswerRepository().findAll().stream().filter((Answer answer) -> answer.getUserId() == user.getId()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public void removeAnswer(Answer answer)
    {
        repositoryFactory.createAnswerRepository().remove(answer);
    }


}
