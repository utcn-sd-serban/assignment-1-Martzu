package com.example.assig1.controller;

import com.example.assig1.exception.UserNotFoundException;
import com.example.assig1.model.*;
import com.example.assig1.persistence.api.AnswerRepository;
import com.example.assig1.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
@RequiredArgsConstructor
// Command line runners are executed by Spring after the initialization of the app has been done
// https://www.baeldung.com/spring-boot-console-app
public class ConsoleController implements CommandLineRunner {
	private final Scanner scanner = new Scanner(System.in);
	private final UserService userService;
	private final QuestionService questionService;
	private final QuestionTagService questionTagService;
	private final TagService tagService;
	private final AnswerService answerService;
	private final VoteAnswerService voteAnswerService;
	private final QuestionVoteService questionVoteService;


	private String currentUserName;
	//TODO

	@Override
	public void run(String... args) {
		boolean done = false;
		handleLogin();
		while (!done) {

			print("Enter a command: ");
			String command = scanner.nextLine().trim();
			try {
				done = handleCommand(command);
			} catch (UserNotFoundException userNotFoundException) {
				print("User not found");
			}
		}
	}

	private void handleLogin()
	{
		boolean successfull = false;
		while(!successfull)
		{
			print("Please enter your full name:");
			String fullName = scanner.nextLine().trim();
			print("Password:");
			String password = scanner.nextLine().trim();
			successfull = userService.verifyUser(fullName, password);
			if(successfull)
			{
				currentUserName = fullName;
				print("Greetings!");
			}
		}

	}

	private boolean handleCommand(String command) {
		switch (command) {
			case "Search a question":
				handleSearchQuestion();
				return false;

			case "Add question":
				handleAddQuestion();
				return false;

			case "List questions":
				handleListQuestions();
				return false;

			case "Help":
				handleHelp();
				return false;

			case "Answer question":
				handleAnswerQuestion();
				return false;

			case "Edit answer":
				handleAnswerEdit();
				return false;

			case "Vote question":
				handleVoteQuestion();
				return false;

			case "Vote answer":
				handleAnswerVote();
				return false;
			case "Exit":
				return true;
			default:
				print("Unknown command. Try again.");
				return false;
		}
	}

	public void handleVoteQuestion()
	{
		print("Enter question's title");
		String title = scanner.nextLine().trim();

		Question question = questionService.searchByTitle(title).get();

		User user = userService.findUserByName(currentUserName).get();

        if(user.getId() == question.getUserId())
        {
            print("You can't vote your own question!");
            return;
        }

        if(questionVoteService.alreadyVoted(user, question).isPresent())
        {
            print("Already voted");
            print("Type to change to downvote from upvote and vice-versa or delete to remove it");
            String option = scanner.nextLine().trim();
            VoteQuestion voteQuestion = questionVoteService.alreadyVoted(user,question).get();
            if(option.equals("change"))
            {
                if(voteQuestion.getType().equals("up"))
                {
                    voteQuestion.setType("down");
                }
                else
                {
                    voteQuestion.setType("up");
                }
                questionVoteService.save(voteQuestion);
            }
            else
            {
                questionVoteService.remove(voteQuestion);
            }
        }
        print("up - upvote down - downvote");
        String type = scanner.nextLine().trim();

        questionVoteService.save(new VoteQuestion(0, user.getId(), question.getId(), type));

        print(type + "vote submitted for " + title);

		//TODO plus service pt questionvote
	}

	public void handleAnswerVote()
	{
		print("Enter question's title");
		String title = scanner.nextLine().trim();

		Question question = questionService.searchByTitle(title).get();

		User user = userService.findUserByName(currentUserName).get();

		List<Answer> answers = answerService.listAnswersForQuestion(question);

		//answers = voteAnswerService.sortByVotes(answers);

		displayAnswers(answers);

		print("Choose the answer to vote");

		int currentAnswer = Integer.parseInt(scanner.nextLine().trim());

		if(answers.get(currentAnswer - 1).getUserId() == user.getId())
        {
            print("You can't vote your own answer");
            return;
        }

		if(voteAnswerService.alreadyVoted(user,answers.get(currentAnswer - 1)).isPresent())
		{
			print("Already voted");
			print("Type change to downvote from upvote and vice-versa or delete to remove it");
			VoteAnswer voteAnswer = voteAnswerService.alreadyVoted(user, answers.get(currentAnswer - 1)).get();
			String option = scanner.nextLine().trim();
			if(option.equals("change"))
			{

				if(voteAnswer.getType().equals("up"))
				{
					voteAnswer.setType("down");
				}
				else
				{
					voteAnswer.setType("up");
				}
				voteAnswerService.save(voteAnswer);
			}
			else
			{
				voteAnswerService.remove(voteAnswer);
			}
		}
		else
		{
			print("up - upvote down - downvote");
			String type = scanner.nextLine().trim();
			VoteAnswer voteAnswer = new VoteAnswer(0, user.getId(), answers.get(currentAnswer - 1).getId(),type);
			voteAnswerService.save(voteAnswer);
		}



	}

	private void displayAnswers(List<Answer> answers) {
		voteAnswerService.sortByVotes(answers);
		int i = 0;
		for(Answer answer : answers)
		{
			System.out.println(++i + ".");
			print("Votes:" + voteAnswerService.countVotes(answer));
			print(answer.getText());
			print("Created by " + userService.findById(answer.getUserId()).get().getFullName());
			print(answer.getCreationDate().toString());
			print("");
		}
	}

	public void handleHelp()
	{
		print("Commands:");
		print("Search a question");
		print("Add question");
		print("List questions");
		print("Answer question");
		print("Exit");
		print("");
	}

	public void handleAnswerEdit()
	{
		List<Answer> userAnswers = answerService.listAnswersFromUser(userService.findUserByName(currentUserName).get());

		print("Your answers:");
		print("");
		int currentAnswer = 0;
		for(Answer answer : userAnswers)
		{
			System.out.println(++currentAnswer + ".");
			print(answer.getText() + " for question " + questionService.findById(answer.getQuestionId()).get().getTitle() );
		}

		boolean done = false;
		while(!done)
		{
			print("Press exit to stop editing or removing or the answer's number to edit it");
			String input = scanner.nextLine().trim();
			if(input.equals("exit"))
			{
				done = true;
			}
			else
			{
				try
				{
					currentAnswer = Integer.parseInt(input);
					if(currentAnswer > userAnswers.size())
					{
						print("That question does not exist");
					}
					else
					{
						print(userAnswers.get(currentAnswer - 1).getText());
						print("Type edit or delete");
						String action = scanner.nextLine().trim();
						if(action.equals("edit"))
						{
							print("Enter your new response");
							String text = scanner.nextLine();
							userAnswers.get(currentAnswer - 1).setText(text);
							answerService.saveAnswer(userAnswers.get(currentAnswer - 1));
							print("Edited!");
						}
						if(action.equals("delete"))
						{
							answerService.removeAnswer(userAnswers.get(currentAnswer - 1));
							print("Deleted!");
						}
					}
				}
				catch (NumberFormatException exception)
				{
					print("Answer number not introduced");
				}
			}
		}
	}

	public void handleListQuestions()
	{
		List<Question> questions = questionService.findAll();
		Collections.reverse(questions);
		for(Question question : questions)
		{

			print(question.toString());

            if(questionVoteService.findByQuestionId(question.getId()).isPresent())
            {
                System.out.println("Votes: " + questionVoteService.countVotes(questionVoteService.findByQuestionId(question.getId()).get()));

            }
            else
            {
                print("No votes");
            }
			print("Created by " + userService.findById(questionService.searchByTitle(question.getTitle()).get().getUserId()).get().getFullName());
			print("");

		}
	}

	public void handleAddQuestion()
	{
		//cand creez taguri, trebuie sa fac si questionTag
		print("Create a title for your question");
		String title = scanner.nextLine().trim();

		//daca cumva exista itnrebarea
		//point user to that question instead
		if(questionService.searchByTitle(title).isPresent())
		{
			print("Question already exists!");
			print("Please search for the following question instead:");
			print(questionService.searchByTitle(title).get().getTitle());
		}
		else
		{
			print("What is your problem?");
			String text = scanner.nextLine().trim();
			Date creationDate = new Date();
			Question question = questionService.saveQuestion(new Question(0, userService.findUserByName(currentUserName).get().getId(), title, text, creationDate));
			boolean doneWithTags = false;
			while(!doneWithTags)
			{
				print("Type exit to stop adding tags or enter your tag to be added");
				String tagText = scanner.nextLine().trim();
				if(tagText.equals("exit"))
				{
					doneWithTags = true;
				}
				else
				{
					if (tagService.findByText(tagText).isPresent())
					{
						questionTagService.addTag(question, tagService.findByText(tagText).get());
					}
					else
					{
						Tag tag = tagService.saveTag(new Tag(0, tagText));
						questionTagService.addTag(question, tag);
					}
				}

			}
		}


	}

	public void handleAnswerQuestion()
	{
		print("Please first search for the question title by using Search a question or just input the question's title");
		String option = scanner.nextLine().trim();
		if(option.equals("Search a question"))
		{
			handleSearchQuestion();
		}
		else
		{
			print("Enter your answer");
			String answerText = scanner.nextLine().trim();
			Date date = new Date();
			answerService.saveAnswer(new Answer(0, questionService.searchByTitle(option).get().getId(), userService.findUserByName(currentUserName).get().getId(), answerText, date));
			print("Answer saved!");
			print("");
		}

	}

	public void handleSearchQuestion()
	{
		//use tag only to identify the question's title
		//search the question title to get a full thread
		print("Search by title or by tag?");
		String type = scanner.nextLine().trim();
		if (type.equals("title"))
		{
			print("Type the title");
			String title = scanner.nextLine().trim();
			if(questionService.searchByTitle(title).isPresent() == false)
			{
				print("No such question exists");
			}
			else
			{
				print(questionService.searchByTitle(title).get().toString());
                //System.out.println(questionVoteService.countVotes(questionVoteService.findByQuestionId(questionService.searchByTitle(title).get().getId())));
                if(questionVoteService.findByQuestionId(questionService.searchByTitle(title).get().getId()).isPresent())
                {
                    System.out.println("Votes: " + questionVoteService.countVotes(questionVoteService.findByQuestionId(questionService.searchByTitle(title).get().getId()).get()));

                }
                else
                {
                    print("No votes");
                }
				print(userService.findById(questionService.searchByTitle(title).get().getUserId()).get().getFullName());
				print("");
				List<Answer> answers = answerService.listAnswersForQuestion(questionService.searchByTitle(title).get());
				print("Answers:");
				print("");
				displayAnswers(answers);
			}
		}
		if(type.equals("tag"))
		{
			print("Type the tag");
			String tagText = scanner.nextLine().trim();
			//tagu meu trebuie sa aiba acelasi id ca ala in baza de date
			Optional<Tag> tag = tagService.findByText(tagText);
			if(tag.isPresent() == false)
			{
				print("Tag does not exist");
			}
			else
			{
				List<QuestionTag> tagContainingQuestions = questionTagService.getQuestionsByTag(tag.get());
				List<Question> questionsWithTag = new ArrayList<>();
				//am toate questionurile care continu acel tag
				//ma plimb prin toate, si afisez qestion.findbyid
				for (QuestionTag questionTag : tagContainingQuestions)
				{
					//no need to check if empty
					//pt ca imi scoate toate care exista deja
					questionsWithTag.add(questionService.findById(questionTag.getQuestionId()).get());
					print(questionService.findById(questionTag.getQuestionId()).get().getTitle());
					print(questionService.findById(questionTag.getQuestionId()).get().getText());
					print(questionService.findById(questionTag.getQuestionId()).get().getCreationDate().toString());
					print(userService.findById(questionService.searchByTitle(questionService.findById(questionTag.getQuestionId()).get().getTitle()).get().getUserId()).get().getFullName());


					print("");
				}
			}

		}
	}


	private void print(String value) {
		System.out.println(value);
	}
}
