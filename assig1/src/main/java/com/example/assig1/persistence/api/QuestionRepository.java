package com.example.assig1.persistence.api;

import com.example.assig1.model.Question;


import java.util.List;
import java.util.Optional;

public interface QuestionRepository {


    Question save(Question question);

    Optional<Question> findById(int id);

    void remove(Question question);

    List<Question> findAll();

    Optional<Question> searchByTitle(String title);

}
