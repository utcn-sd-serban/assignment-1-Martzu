package com.example.assig1.persistence.jdbc;

import com.example.assig1.model.Question;
import com.example.assig1.model.QuestionTag;
import com.example.assig1.model.Tag;
import com.example.assig1.persistence.api.QuestionTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcQuestionTagRepository implements QuestionTagRepository {

    private final JdbcTemplate template;


    @Override
    public QuestionTag save(QuestionTag questionTag) {
        if(questionTag.getId() != 0)
        {
            update(questionTag);
        }
        else
        {
            int id = insert(questionTag);
            questionTag.setId(id);
        }
        return questionTag;
    }

    @Override
    public Optional<QuestionTag> findById(int id) {

        List<QuestionTag> questionTags  = template.query("SELECT * FROM question_tag WHERE id = ?",
                (resultSet , i )->
                        new QuestionTag(resultSet.getInt("id"),
                        resultSet.getInt("qid"),
                        resultSet.getInt("tid")), id);

        return questionTags.isEmpty() ? Optional.empty() : Optional.of(questionTags.get(0));
    }

    @Override
    public void remove(QuestionTag questionTag) {
        template.update("DELETE FROM question_tag WHERE id = ?", questionTag.getId());
    }

    @Override
    public List<QuestionTag> findAll() {
        return template.query("SELECT * FROM question_tag", (resultSet , i )->
                new QuestionTag(resultSet.getInt("id"),
                        resultSet.getInt("qid"),
                        resultSet.getInt("tid")));
    }


    private int insert(QuestionTag questionTag)
    {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question_tag");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("id", questionTag.getId());
        data.put("qid", questionTag.getQuestionId());
        data.put("tid", questionTag.getTagId());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(QuestionTag questionTag)
    {
        template.update("UPDATE question_tag SET qid = ?, tid = ?,  WHERE id = ?",questionTag.getQuestionId(), questionTag.getTagId(), questionTag.getId());
    }
}
