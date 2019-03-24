package com.example.assig1.persistence.api;

import com.example.assig1.model.VoteAnswer;
import com.example.assig1.model.VoteQuestion;

import java.util.List;
import java.util.Optional;

public interface VoteQuestionRepository {


    VoteQuestion save(VoteQuestion voteQuestion);

    Optional<VoteQuestion> findById(int id);

    void remove(VoteQuestion voteQuestion);

    List<VoteQuestion> findAll();



}
