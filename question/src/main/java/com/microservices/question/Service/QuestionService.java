package com.microservices.question.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.question.Dao.QuestionDao;
import com.microservices.question.Model.AnswerCheckRequest;
import com.microservices.question.Model.QuestionWrapper;
import com.microservices.question.Model.Questions;

@Service
public class QuestionService {

	@Autowired
	QuestionDao dao;

	public List<Questions> getAllQuestion() {
		return dao.findAll();

	}

	public List<Questions> getQuestionByCategory(String category) {
		return dao.findByCategory(category);
	}

	public String addUser(Questions question) {

		dao.save(question);
		return "Success";
	}

	public List<Integer> getQuestionByQuiz(String category, int num) {
		List<Integer> idList = dao.findByCategoryRandomQuestion(category, num);
		return idList;
	}

	public List<QuestionWrapper> getQuestionByIds(List<Integer> questionIds) {
		List<Questions> questions = new ArrayList<Questions>();
		List<QuestionWrapper> wrappers = new ArrayList<QuestionWrapper>();
		for (Integer integer : questionIds) {
			Questions question = dao.findById(integer).get();
			questions.add(question);

		}
		for (Questions q : questions) {
			QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(), q.getQuestion(), q.getOption_a(),
					q.getOption_b(), q.getOption_c(), q.getOption_d());
			wrappers.add(questionWrapper);
		}
		return wrappers;
	}

	public Integer getScore(List<AnswerCheckRequest> requestList) {
		Integer result = 0;
		for (AnswerCheckRequest request : requestList) {
			Questions q = dao.findById(request.getId()).get();
			if (request.getResponse().equals(q.getRight_answer())) {
				result++;
			}
		}
		return result;
	}
}
