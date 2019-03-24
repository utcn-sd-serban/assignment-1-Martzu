package com.example.assig1.persistence.memory;

import com.example.assig1.model.Tag;
import com.example.assig1.persistence.api.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryTagRepository implements TagRepository {

    private final Map<Integer, Tag> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public Tag save(Tag tag) {
        if (tag.getId() == 0) {//chestia asta ii dubioasa, tre sa ii dau de la inceput 0 cand se creeaza obiectu si nu ii initializat
            tag.setId(currentId.incrementAndGet());
        }
        data.put(tag.getId(), tag);
        return tag;
    }

    @Override
    public Optional<Tag> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(Tag tag) {
        data.remove(tag.getId());
    }

    @Override
    public List<Tag> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Optional<Tag> findByText(String text) {
        return findAll().stream().filter((Tag tag) -> tag.getText().equals(text)).findFirst();
    }
}
