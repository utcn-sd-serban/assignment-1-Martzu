package com.example.assig1.persistence.api;

import com.example.assig1.model.VoteAnswer;

import java.util.List;
import java.util.Optional;

public interface VoteAnswerRepository {


    VoteAnswer save(VoteAnswer voteAnswer);

    Optional<VoteAnswer> findById(int id);

    void remove(VoteAnswer voteAnswer);

    List<VoteAnswer> findAll();



}
