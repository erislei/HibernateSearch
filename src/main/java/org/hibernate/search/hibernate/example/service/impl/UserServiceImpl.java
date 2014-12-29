package org.hibernate.search.hibernate.example.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.hibernate.search.hibernate.example.dao.UserDao;
import org.hibernate.search.hibernate.example.model.QueryResult;
import org.hibernate.search.hibernate.example.model.User;
import org.hibernate.search.hibernate.example.service.UserService;
import org.springframework.stereotype.Service;

@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

	@Resource(name="userDaoImpl")
	private UserDao userDao;

	@Override
	public void add(User user) {
		userDao.add(user);

	}

	@Override
	public List<User> query(int start, int pagesize) {
		return userDao.query(start, pagesize);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public void delete(int id) {
		userDao.delete(id);
	}

	@Override
	public QueryResult<User> query(String keyword, int start, int pagesize,
			Analyzer analyzer, String... field) throws Exception {
		return userDao.query(keyword, start, pagesize, analyzer, field);
	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}

}
