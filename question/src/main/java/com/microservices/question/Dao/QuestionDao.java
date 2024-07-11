package com.microservices.question.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservices.question.Model.Questions;

@Repository
public interface QuestionDao extends JpaRepository<Questions, Integer> {

	List<Questions> findByCategory(String category);

	@Query(value = "select q.id from questions q where q.category = :category order by rand() limit :num", nativeQuery = true)
	List<Integer> findByCategoryRandomQuestion(String category, int num);

}
