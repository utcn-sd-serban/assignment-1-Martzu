package com.example.assig1.service;

import com.example.assig1.model.Question;
import com.example.assig1.model.QuestionTag;
import com.example.assig1.model.Tag;
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
public class QuestionTagService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public void addTag(Question question, Tag tag)
    {
        QuestionTag questionTag = new QuestionTag(0, question.getId(), tag.getId());
        repositoryFactory.createQuestionTagRepository().save(questionTag);
    }

    @Transactional
    public List<QuestionTag> getTagsForQuestion(Question question)
    {
        //returns all questions tags for a question
        //must return all the tags
        return repositoryFactory.createQuestionTagRepository().findAll().stream().filter((QuestionTag questionTag) -> questionTag.getQuestionId() == question.getId()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional
    public List<QuestionTag> getQuestionsByTag(Tag tag)
    {
        //mai intai iau tagurile cu functia asta
        //in controller, ma folosesc de questionRepository sa le gasesc pe toate care au match cu id-u question tagurilor scoase de aicea
        return repositoryFactory.createQuestionTagRepository().findAll().stream().filter((QuestionTag questionTag) -> questionTag.getTagId() == tag.getId()).collect(Collectors.toCollection(ArrayList::new));
    }



}
