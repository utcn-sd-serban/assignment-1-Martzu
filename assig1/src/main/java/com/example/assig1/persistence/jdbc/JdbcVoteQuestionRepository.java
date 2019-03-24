package com.example.assig1.persistence.jdbc;

import com.example.assig1.model.VoteQuestion;
import com.example.assig1.persistence.api.VoteQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcVoteQuestionRepository implements VoteQuestionRepository {

    private final JdbcTemplate template;


    @Override
    public VoteQuestion save(VoteQuestion voteQuestion) {
        if(voteQuestion.getId() != 0)
        {
            update(voteQuestion);
        }
        else
        {
            int id = insert(voteQuestion);
            voteQuestion.setId(id);
        }
        return voteQuestion;
    }

    @Override
    public Optional<VoteQuestion> findById(int id) {

        List<VoteQuestion> voteQuestions = template.query("SELECT * FROM vote_question WHERE id = ?",
                (resultSet , i )->
                        new VoteQuestion(resultSet.getInt("id"),
                                resultSet.getInt("question_id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("vote_type")), id);

        return voteQuestions.isEmpty() ? Optional.empty() : Optional.of(voteQuestions.get(0));
    }

    public void remove(VoteQuestion voteQuestion) {
        template.update("DELETE FROM vote_question WHERE id = ?", voteQuestion.getId());
    }

    @Override
    public List<VoteQuestion> findAll() {
        return template.query("SELECT * FROM vote_question", (resultSet , i )->
                new VoteQuestion(resultSet.getInt("id"),
                        resultSet.getInt("question_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("vote_type")));
    }



    private int insert(VoteQuestion voteQuestion)
    {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("vote_question");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", voteQuestion.getUserId());
        data.put("question_id", voteQuestion.getQuestionId());
        data.put("vote_type", voteQuestion.getType());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(VoteQuestion voteQuestion)
    {
        template.update("UPDATE vote_question SET user_id = ?, Question_id = ?, vote_type = ?,  WHERE id = ?", voteQuestion.getUserId(), voteQuestion.getQuestionId(), voteQuestion.getType(), voteQuestion.getId());
    }
}
