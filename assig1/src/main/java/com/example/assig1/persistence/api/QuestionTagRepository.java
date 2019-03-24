package com.example.assig1.persistence.api;

import com.example.assig1.model.Question;
import com.example.assig1.model.QuestionTag;
import com.example.assig1.model.Tag;

import java.util.List;
import java.util.Optional;

public interface QuestionTagRepository {


    QuestionTag save(QuestionTag questionTag);

    Optional<QuestionTag> findById(int id);

    void remove(QuestionTag questionTag);

    List<QuestionTag> findAll();

    //List<Tag> getTagsForQuestion(Question question);
}
