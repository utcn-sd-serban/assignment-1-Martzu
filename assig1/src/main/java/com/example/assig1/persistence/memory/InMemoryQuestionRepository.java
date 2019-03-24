package com.example.assig1.persistence.memory;

import com.example.assig1.model.Question;
import com.example.assig1.persistence.api.QuestionRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryQuestionRepository implements QuestionRepository {

    private final Map<Integer, Question> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public Question save(Question question) {
        if (question.getId() == 0) {//chestia asta ii dubioasa, tre sa ii dau de la inceput 0 cand se creeaza obiectu si nu ii initializat
            question.setId(currentId.incrementAndGet());
        }
        data.put(question.getId(), question);
        return question;
    }

    @Override
    public Optional<Question> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(Question question) {
        data.remove(question.getId());
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Question> searchByTitle(String title) {
        return findAll().stream().filter((Question question) -> question.getTitle().equals(title)).findFirst();
    }



}
