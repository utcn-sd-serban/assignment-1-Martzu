package com.example.assig1.persistence.jdbc;

import com.example.assig1.model.Question;
import com.example.assig1.model.User;
import com.example.assig1.persistence.api.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcQuestionRepository implements QuestionRepository {

    private final JdbcTemplate template;


    @Override
    public Question save(Question question) {
        if(question.getId() != 0)
        {
            update(question);
        }
        else
        {
            int id = insert(question);
            question.setId(id);
        }
        return question;
    }

    @Override
    public Optional<Question> findById(int id) {

        List<Question> questions  = template.query("SELECT * FROM question WHERE id = ?",
                (resultSet , i )->
                        new Question(resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("creation_date")), id);

        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void remove(Question question) {
        template.update("DELETE FROM question WHERE id = ?", question.getId());
    }

    @Override
    public List<Question> findAll() {
        return template.query("SELECT * FROM question", (resultSet , i )->
                new Question(resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getDate("creation_date")));
    }

    @Override
    public Optional<Question> searchByTitle(String title) {
        return findAll().stream().filter((Question question) -> question.getTitle().equals(title)).findFirst();
    }


    private int insert(Question question)
    {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", question.getUserId());
        data.put("title", question.getTitle());
        data.put("text", question.getText());
        data.put("creation_date", question.getCreationDate());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Question question)
    {
        template.update("UPDATE question SET title = ?, text = ? WHERE id = ?",question.getTitle(), question.getText(), question.getId());
    }
}
