package com.example.assig1.service;

import com.example.assig1.model.Question;
import com.example.assig1.model.Tag;
import com.example.assig1.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.QueryEval;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QuestionService {
    //creezi taguri, dai edit, delete
    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<Question> listAllQuestions()
    {
        return repositoryFactory.createQuestionRepository().findAll();
    }

    @Transactional
    public void removeQuestion(Question question)
    {
        repositoryFactory.createQuestionRepository().remove(question);
    }


    @Transactional//use it for both insert and update
    public Question saveQuestion(Question question)
    {
        return repositoryFactory.createQuestionRepository().save(question);
    }

    @Transactional
    public Optional<Question> findById(int id)
    {
        return repositoryFactory.createQuestionRepository().findById(id);
    }


    @Transactional
    public List<Question> findAll()
    {
        return repositoryFactory.createQuestionRepository().findAll();
        //pt search by tags, fa folosesc de findall si filtrez sa aiba question id la fel cu cel din tabelu care contine si questionu si tagu
        //folosesc questionsTagService.findQuestionByTag(Tag tag)
        //pot sa ma folosesc tot de findall, sa gasesc toate chestiile si sa verific daca title.equals(ce am dat eu sa caut)
    }

    @Transactional
    public Optional<Question> searchByTitle(String title)
    {
        return repositoryFactory.createQuestionRepository().searchByTitle(title);
    }


    //TODO search for question(dupa nume) -- cauti hashmpapu pana se rupe si gasesti questionu


}
