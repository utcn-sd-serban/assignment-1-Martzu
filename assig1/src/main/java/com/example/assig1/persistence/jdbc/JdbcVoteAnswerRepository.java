package com.example.assig1.persistence.jdbc;

import com.example.assig1.model.VoteAnswer;
import com.example.assig1.persistence.api.VoteAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcVoteAnswerRepository implements VoteAnswerRepository {

    private final JdbcTemplate template;


    @Override
    public VoteAnswer save(VoteAnswer voteAnswer) {
        if(voteAnswer.getId() != 0)
        {
            update(voteAnswer);
        }
        else
        {
            int id = insert(voteAnswer);
            voteAnswer.setId(id);
        }
        return voteAnswer;
    }

    @Override
    public Optional<VoteAnswer> findById(int id) {

        List<VoteAnswer> voteAnswers = template.query("SELECT * FROM vote_answer WHERE id = ?",
                (resultSet , i )->
                        new VoteAnswer(resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("answer_id"),
                                resultSet.getString("vote_type")), id);

        return voteAnswers.isEmpty() ? Optional.empty() : Optional.of(voteAnswers.get(0));
    }

    public void remove(VoteAnswer voteAnswer) {
        template.update("DELETE FROM vote_answer WHERE id = ?", voteAnswer.getId());
    }

    @Override
    public List<VoteAnswer> findAll() {
        return template.query("SELECT * FROM vote_answer", (resultSet , i )->
                new VoteAnswer(resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("answer_id"),
                        resultSet.getString("vote_type")));
    }



    private int insert(VoteAnswer voteAnswer)
    {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("vote_answer");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("user_id", voteAnswer.getUserId());
        data.put("answer_id", voteAnswer.getAnswerId());
        data.put("vote_type", voteAnswer.getType());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(VoteAnswer voteAnswer)
    {
        template.update("UPDATE vote_answer SET user_id = ?, answer_id = ?, vote_type = ? WHERE id = ?", voteAnswer.getUserId(), voteAnswer.getAnswerId(), voteAnswer.getType(), voteAnswer.getId());
    }
}
