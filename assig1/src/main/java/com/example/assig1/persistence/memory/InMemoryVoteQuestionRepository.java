package com.example.assig1.persistence.memory;

import com.example.assig1.model.VoteAnswer;
import com.example.assig1.model.VoteQuestion;
import com.example.assig1.persistence.api.VoteAnswerRepository;
import com.example.assig1.persistence.api.VoteQuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryVoteQuestionRepository implements VoteQuestionRepository {
    private final Map<Integer, VoteQuestion> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public VoteQuestion save(VoteQuestion voteQuestion) {
        if (voteQuestion.getId() == 0) {
            voteQuestion.setId(currentId.incrementAndGet());
        }
        data.put(voteQuestion.getId(), voteQuestion);
        return voteQuestion;
    }

    @Override
    public Optional<VoteQuestion> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(VoteQuestion voteQuestion) {
        data.remove(voteQuestion.getId());
    }

    @Override
    public List<VoteQuestion> findAll() {
        return new ArrayList<>(data.values());
    }



}
