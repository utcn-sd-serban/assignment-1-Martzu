package com.example.assig1.service;


import com.example.assig1.model.Answer;
import com.example.assig1.model.User;
import com.example.assig1.model.VoteAnswer;
import com.example.assig1.persistence.api.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteAnswerService {

    private final RepositoryFactory repositoryFactory;

    @Transactional
    public VoteAnswer save(VoteAnswer voteAnswer)
    {
        //both to update and insert
        return repositoryFactory.createVoteAnswerRepository().save(voteAnswer);
    }


    @Transactional
    public void remove(VoteAnswer voteAnswer)
    {
        repositoryFactory.createVoteAnswerRepository().remove(voteAnswer);
    }

    @Transactional
    public Optional<VoteAnswer> alreadyVoted(User user, Answer answer)
    {
        return repositoryFactory.createVoteAnswerRepository().findAll().stream().filter((VoteAnswer voteAnswer) -> voteAnswer.getUserId() == user.getId() && voteAnswer.getAnswerId() == answer.getId()).findFirst();
    }


    @Transactional
    public long countUpVotes(Answer answer)
    {
        return repositoryFactory.createVoteAnswerRepository().findAll().stream().filter((VoteAnswer voteAnswer) ->  voteAnswer.getAnswerId() == answer.getId() && voteAnswer.getType().equals("up")).count();
    }

    @Transactional
    public long countDownVotes(Answer answer)
    {
        return repositoryFactory.createVoteAnswerRepository().findAll().stream().filter((VoteAnswer voteAnswer) ->  voteAnswer.getAnswerId() == answer.getId() && voteAnswer.getType().equals("down")).count();
    }

    @Transactional
    public long countVotes(Answer answer)
    {
        return countUpVotes(answer) - countDownVotes(answer);
    }


    @Transactional
    public List<Answer> sortByVotes(List<Answer> answers)
    {
        Collections.sort(answers, new Comparator<Answer>() {
            @Override
            public int compare(Answer o1, Answer o2) {
                return countVotes(o1) > countVotes(o2) ? -1 : (countVotes(o1) < countVotes(o2)) ? 1 : 0;
            }
        });
        return answers;

    }






}
