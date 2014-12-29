package org.hibernate.search.hibernate.example.service;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.hibernate.search.hibernate.example.model.QueryResult;
import org.hibernate.search.hibernate.example.model.User;

public interface UserService {

	public void add(User user);

	public List<User> query(int start, int pagesize);

	public void update(User user);

	public void delete(User user) ;

	public void delete(int id) ;

	public QueryResult<User> query(String keyword, int start, int pagesize,
			Analyzer analyzer, String... field) throws Exception ;
	public User load(int id) ;
}
