package com.example.assig1.persistence.api;


import com.example.assig1.model.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {


    Answer save(Answer answer);

    Optional<Answer> findById(int id);

    void remove(Answer answer);

    List<Answer> findAll();

}
