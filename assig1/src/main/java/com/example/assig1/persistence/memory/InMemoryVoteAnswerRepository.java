package com.example.assig1.persistence.memory;

import com.example.assig1.model.VoteAnswer;
import com.example.assig1.persistence.api.VoteAnswerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryVoteAnswerRepository implements VoteAnswerRepository {
    private final Map<Integer, VoteAnswer> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public VoteAnswer save(VoteAnswer voteAnswer) {
        if (voteAnswer.getId() == 0) {
            voteAnswer.setId(currentId.incrementAndGet());
        }
        data.put(voteAnswer.getId(), voteAnswer);
        return voteAnswer;
    }

    @Override
    public Optional<VoteAnswer> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(VoteAnswer voteAnswer) {
        data.remove(voteAnswer.getId());
    }

    @Override
    public List<VoteAnswer> findAll() {
        return new ArrayList<>(data.values());
    }



}
