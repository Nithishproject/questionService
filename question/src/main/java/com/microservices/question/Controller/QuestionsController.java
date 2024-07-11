package com.microservices.question.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.question.Model.AnswerCheckRequest;
import com.microservices.question.Model.QuestionWrapper;
import com.microservices.question.Model.Questions;
import com.microservices.question.Service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionsController {
	@Autowired
	QuestionService questionService;
	
	@Autowired
	Environment environment;

	@GetMapping("/getAllQuestions")
	public ResponseEntity<List<Questions>> getAllQuestions() {
		List<Questions> questionList = new ArrayList<>();
		try {
			questionList = questionService.getAllQuestion();
		} catch (Exception e) {
			e.getMessage();
		}
		return new ResponseEntity<>(questionList, HttpStatus.OK);
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<List<Questions>> getQuestionByCategory(@PathVariable String category) {
		List<Questions> questionList = new ArrayList<>();
		try {
			questionList = questionService.getQuestionByCategory(category);
		} catch (Exception e) {
			e.getMessage();
		}
		return new ResponseEntity<>(questionList, HttpStatus.OK);
	}

	@PutMapping("/addQuestion")
	public ResponseEntity<String> addQuestion(@RequestBody Questions question) {
		String responseData = "";
		try {
			responseData = questionService.addUser(question);
		} catch (Exception e) {
			e.getMessage();
		}
		return new ResponseEntity<>(responseData, HttpStatus.CREATED);		

	}
	
	@GetMapping("generate/{category}/{num}")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@PathVariable String category, @PathVariable int num){
		System.out.println(environment.getProperty("local.server.port"));
		List<Integer> idList = questionService.getQuestionByQuiz(category, num);
 		return new ResponseEntity<List<Integer>>(idList, HttpStatus.OK);
	}
	
	@PostMapping("getQuestionsForQuiz")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsForQuiz(@RequestBody List<Integer> questionIds){
		List<QuestionWrapper> wrappers = questionService.getQuestionByIds(questionIds);
 		return new ResponseEntity<List<QuestionWrapper>>(wrappers, HttpStatus.OK);
	}
	
	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore (@RequestBody List<AnswerCheckRequest> requestList){
		System.out.println(environment.getProperty("local.server.port"));
		Integer result = questionService.getScore(requestList);
		return new ResponseEntity<Integer>(result, HttpStatus.OK );
	}

}
