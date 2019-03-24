package com.example.assig1.persistence.api;

import com.example.assig1.model.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository {


    Tag save(Tag tag);

    Optional<Tag> findById(int id);

    void remove(Tag tag);

    List<Tag> findAll();

    Optional<Tag> findByText(String text);
}
