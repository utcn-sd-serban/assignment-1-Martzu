package com.example.assig1.service;


import com.example.assig1.model.Question;
import com.example.assig1.model.User;
import com.example.assig1.model.VoteAnswer;
import com.example.assig1.model.VoteQuestion;
import com.example.assig1.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionVoteService {

    private final RepositoryFactory repositoryFactory;


    @Transactional
    public VoteQuestion save(VoteQuestion voteQuestion)
    {
        return repositoryFactory.createVoteQuestionRepository().save(voteQuestion);
    }

    @Transactional
    public void remove(VoteQuestion voteQuestion)
    {
        repositoryFactory.createVoteQuestionRepository().remove(voteQuestion);
    }


    public Optional<VoteQuestion> findByQuestionId(int questionId)
    {
        return repositoryFactory.createVoteQuestionRepository().findAll().stream().filter((VoteQuestion vote) -> vote.getQuestionId() == questionId).findFirst();
    }


    @Transactional
    public long countVotes(VoteQuestion voteQuestion)
    {
        return repositoryFactory.createVoteQuestionRepository().findAll().stream().filter((VoteQuestion vote) -> vote.getId() == voteQuestion.getId() && vote.getType().equals("up")).count() -
        repositoryFactory.createVoteQuestionRepository().findAll().stream().filter((VoteQuestion vote) -> vote.getId() == voteQuestion.getId() && vote.getType().equals("down")).count();

    }

    @Transactional
    public Optional<VoteQuestion> alreadyVoted(User user, Question question)
    {
        return repositoryFactory.createVoteQuestionRepository().findAll().stream().filter((VoteQuestion vote) -> vote.getUserId() == user.getId() && vote.getQuestionId() == question.getId()).findFirst();
    }
}
