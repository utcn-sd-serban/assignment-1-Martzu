package com.example.assig1;

import com.example.assig1.exception.UserNotFoundException;
import com.example.assig1.model.*;
import com.example.assig1.persistence.api.RepositoryFactory;
import com.example.assig1.persistence.memory.InMemoryRepositoryFactory;
import com.example.assig1.service.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


public class Assig1ApplicationTests {

	private static RepositoryFactory createMockedFactory() {
		RepositoryFactory factory = new InMemoryRepositoryFactory();
		factory.createUserRepository().save(new User(0, "A.FN", "A.LN", "A.EA"));
		factory.createUserRepository().save(new User(0, "B.FN", "B.LN", "B.EA"));
		return factory;
	}

	@Test
	public void testRemoveWorksWithExistingId() {
		// arrange - create a mocked factory and a service backed up by this factory
		RepositoryFactory factory = createMockedFactory();
		UserService service = new UserService(factory);

		service.removeUser(service.findById(1).get());

		Assert.assertEquals(1, factory.createUserRepository().findAll().size());
		Assert.assertTrue(factory.createUserRepository().findById(2).isPresent());
	}

	@Test
	public void testAddQuestion()
	{
		RepositoryFactory factory = createMockedFactory();
		QuestionService questionService = new QuestionService(factory);
		factory.createQuestionRepository().save(new Question(1, "Heey", "It works", new Date()));
		Assert.assertEquals(1, factory.createQuestionRepository().findAll().size());
		Assert.assertTrue(factory.createQuestionRepository().findById(1).isPresent());
	}

	@Test
	public void addTagToQuestion()
	{
		RepositoryFactory factory = createMockedFactory();
		factory.createTagRepository().save(new Tag("java"));
		factory.createQuestionRepository().save(new Question(1, "Heey", "It works", new Date()));
		QuestionTagService questionTagService = new QuestionTagService(factory);
		questionTagService.addTag(factory.createQuestionRepository().findById(1).get(), factory.createTagRepository().findById(1).get());
		Assert.assertEquals(1, factory.createQuestionTagRepository().findAll().size());
		Assert.assertTrue(factory.createQuestionTagRepository().findById(1).isPresent());
	}

	@Test
	public void getAnswersForQuestion()
	{
		RepositoryFactory factory = createMockedFactory();
		AnswerService answerService = new AnswerService(factory);

		factory.createUserRepository().save(new User(0, "A", "A", "A@yahoo.com"));
		factory.createQuestionRepository().save(new Question(1, "Heey", "It works", new Date()));
		answerService.saveAnswer(factory.createAnswerRepository().save(new Answer(0, 1, 1, "To mee as well", new Date())));
		answerService.saveAnswer(factory.createAnswerRepository().save(new Answer(0, 1, 1, "Problem solved", new Date())));

		Assert.assertEquals(2, factory.createAnswerRepository().findAll().size());
		Assert.assertTrue(factory.createAnswerRepository().findById(1).isPresent() && factory.createAnswerRepository().findById(2).isPresent());

	}

	@Test
	public void addVoteQuestion()
	{
		RepositoryFactory factory = createMockedFactory();

		factory.createUserRepository().save(new User(0, "A", "A", "A@yahoo.com"));
		factory.createUserRepository().save(new User(0, "B", "B", "B@yahoo.com"));
		factory.createQuestionRepository().save(new Question(1, "Heey", "It works", new Date()));
		QuestionVoteService questionVoteService = new QuestionVoteService(factory);

		factory.createVoteQuestionRepository().save(new VoteQuestion(0, 2, 1, "up"));

		Assert.assertTrue(questionVoteService.alreadyVoted(factory.createUserRepository().findById(2).get(), factory.createQuestionRepository().findById(1).get()).isPresent());


	}


}
