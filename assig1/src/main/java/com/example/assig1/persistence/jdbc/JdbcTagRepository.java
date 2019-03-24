package com.example.assig1.persistence.jdbc;

import com.example.assig1.model.Question;
import com.example.assig1.model.Tag;
import com.example.assig1.persistence.api.QuestionRepository;
import com.example.assig1.persistence.api.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {

    private final JdbcTemplate template;


    @Override
    public Tag save(Tag tag) {
        if(tag.getId() != 0)
        {
            update(tag);
        }
        else
        {
            int id = insert(tag);
            tag.setId(id);
        }
        return tag;
    }

    @Override
    public Optional<Tag> findById(int id) {

        List<Tag> tags  = template.query("SELECT * FROM tag WHERE id = ?",
                (resultSet , i )->
                        new Tag(resultSet.getInt("id"),
                        resultSet.getString("text")), id);

        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public void remove(Tag tag) {
        template.update("DELETE FROM tag WHERE id = ?", tag.getId());
    }

    @Override
    public List<Tag> findAll() {
        return template.query("SELECT * FROM tag", (resultSet , i )->
                new Tag(resultSet.getInt("id"),

                        resultSet.getString("text")));
    }

    @Override
    public Optional<Tag> findByText(String text) {
        return findAll().stream().filter((Tag tag) -> tag.getText().equals(text)).findFirst();
    }

    private int insert(Tag tag)
    {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("tag");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("id", tag.getId());
        data.put("text", tag.getText());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Tag tag)
    {
        template.update("UPDATE tag SET text = ? WHERE id = ?",tag.getText(), tag.getId());
    }
}
