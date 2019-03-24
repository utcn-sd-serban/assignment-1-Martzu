package com.example.assig1.persistence.memory;

import com.example.assig1.model.Answer;
import com.example.assig1.persistence.api.AnswerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAnswerRepository implements AnswerRepository {
    private final Map<Integer, Answer> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public Answer save(Answer answer) {
        if (answer.getId() == 0) {//chestia asta ii dubioasa, tre sa ii dau de la inceput 0 cand se creeaza obiectu si nu ii initializat
            answer.setId(currentId.incrementAndGet());
        }
        data.put(answer.getId(), answer);
        return answer;
    }

    @Override
    public Optional<Answer> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(Answer answer) {
        data.remove(answer.getId());
    }

    @Override
    public List<Answer> findAll() {
        return new ArrayList<>(data.values());
    }

}
