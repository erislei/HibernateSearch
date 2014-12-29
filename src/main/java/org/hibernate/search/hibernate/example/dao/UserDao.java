package org.hibernate.search.hibernate.example.dao;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.hibernate.search.hibernate.example.model.Book;
import org.hibernate.search.hibernate.example.model.QueryResult;
import org.hibernate.search.hibernate.example.model.User;

public interface UserDao {

	void add(User user);

	User load(int id);

	List<User> query(int start, int pagesize);

	void update(User user);

	void delete(User user);

	void delete(int id);

	/**
	 * 
	 * @param keyword
	 * @param start
	 * @param pagesize
	 * @param analyzer
	 * @param field
	 * @return
	 * @throws Exception
	 */
	QueryResult<User> query(String keyword, int start, int pagesize,
			Analyzer analyzer, String... field) throws Exception;
}
