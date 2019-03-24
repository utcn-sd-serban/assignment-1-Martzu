package com.example.assig1.service;


import com.example.assig1.model.Tag;
import com.example.assig1.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public Tag saveTag(Tag tag)
    {
        return repositoryFactory.createTagRepository().save(tag);
    }


    @Transactional
    public Optional<Tag> findById(int id)
    {
        return repositoryFactory.createTagRepository().findById(id);
    }

    @Transactional
    public void remove(Tag tag)
    {
        repositoryFactory.createTagRepository().remove(tag);
    }


    @Transactional
    public List<Tag> findAll()
    {
        return repositoryFactory.createTagRepository().findAll();
    }

    @Transactional
    public Optional<Tag> findByText(String text)
    {
        return repositoryFactory.createTagRepository().findByText(text);
    }
}
