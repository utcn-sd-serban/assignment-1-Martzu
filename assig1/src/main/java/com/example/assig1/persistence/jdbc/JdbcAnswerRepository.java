package com.example.assig1.persistence.jdbc;


import com.example.assig1.model.Answer;
import com.example.assig1.persistence.api.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcAnswerRepository implements AnswerRepository {

    private final JdbcTemplate template;


    @Override
    public Answer save(Answer answer) {
        if(answer.getId() != 0)
        {
            update(answer);
        }
        else
        {
            int id = insert(answer);
            answer.setId(id);
        }
        return answer;
    }

    @Override
    public Optional<Answer> findById(int id) {

        List<Answer> answers  = template.query("SELECT * FROM answer WHERE id = ?",
                (resultSet , i )->
                        new Answer(resultSet.getInt("id"),
                        resultSet.getInt("question_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("text"),
                        resultSet.getDate("creation_date")), id);

        return answers.isEmpty() ? Optional.empty() : Optional.of(answers.get(0));
    }

    @Override
    public void remove(Answer answer) {
        template.update("DELETE FROM answer WHERE id = ?", answer.getId());
    }

    @Override
    public List<Answer> findAll() {
        return template.query("SELECT * FROM answer", (resultSet , i )->
                new Answer(resultSet.getInt("id"),
                        resultSet.getInt("question_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("text"),
                        resultSet.getDate("creation_date")));
    }



    private int insert(Answer answer)
    {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answer");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("question_id", answer.getQuestionId());
        data.put("user_id", answer.getUserId());
        data.put("text", answer.getText());
        data.put("creation_date", answer.getCreationDate());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Answer answer)
    {
        template.update("UPDATE answer SET text = ? WHERE id = ?", answer.getText(), answer.getId());
    }

    private void delete(Answer answer)
    {
        template.update("DELETE FROM answer WHERE id = ?",answer.getId());
    }

}
