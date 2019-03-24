package com.example.assig1.persistence.memory;

import com.example.assig1.model.QuestionTag;
import com.example.assig1.persistence.api.QuestionTagRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;



public class InMemoryQuestionTagRepository implements QuestionTagRepository {

    private final Map<Integer, QuestionTag> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public QuestionTag save(QuestionTag questionTag) {
        if (questionTag.getId() == 0) {//chestia asta ii dubioasa, tre sa ii dau de la inceput 0 cand se creeaza obiectu si nu ii initializat
            questionTag.setId(currentId.incrementAndGet());
        }
        data.put(questionTag.getId(), questionTag);
        return questionTag;
    }

    @Override
    public Optional<QuestionTag> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(QuestionTag questionTag) {
        data.remove(questionTag.getId());
    }

    @Override
    public List<QuestionTag> findAll() {
        return new ArrayList<>(data.values());
    }
}
