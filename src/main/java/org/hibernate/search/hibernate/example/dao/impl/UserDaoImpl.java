package org.hibernate.search.hibernate.example.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.hibernate.example.dao.UserDao;
import org.hibernate.search.hibernate.example.model.QueryResult;
import org.hibernate.search.hibernate.example.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Repository(value = "userDaoImpl")
public class UserDaoImpl implements UserDao {

	@Resource
	@Qualifier(value="hibernate4sessionFactory")
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void add(User user) {
		getSession().save(user);
	}

	@Override
	public User load(int id) {
		return (User) getSession().load(User.class, id);
	}

	@Override
	public List<User> query(int start, int pagesize) {
		return getSession().createCriteria(User.class).setFirstResult(start)
				.setMaxResults(pagesize).list();
	}

	@Override
	public void update(User user) {
		getSession().merge(user);
	}

	@Override
	public void delete(User user) {
		getSession().delete(user);
	}

	@Override
	public void delete(int id) {
		getSession().delete(load(id));
	}

	@Override
	public QueryResult<User> query(String keyword, int start, int pagesize,
			Analyzer analyzer, String... field) throws Exception {

		QueryResult<User> queryResult = new QueryResult<User>();

		FullTextSession fullTextSession = Search
				.getFullTextSession(getSession());
		MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(
				Version.LUCENE_36, field, new IKAnalyzer());
		Query luceneQuery = multiFieldQueryParser.parse(keyword);

		FullTextQuery fullTextQuery = fullTextSession
				.createFullTextQuery(luceneQuery,User.class);
		int searchResultSize = fullTextQuery.getResultSize();
		queryResult.setSearchresultsize(searchResultSize);
		fullTextQuery.setFirstResult(start);
		fullTextQuery.setMaxResults(pagesize);

		fullTextQuery.setSort(new Sort(new SortField("id", SortField.Type.INT,
				true)));

		SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
				"<b><font color = 'red'>", "</font></b>");
		QueryScorer queryScorer = new QueryScorer(luceneQuery);
		Highlighter highlighter = new Highlighter(simpleHTMLFormatter,
				queryScorer);

		List<User> users = fullTextQuery.list();
		String highlighterString = null;
		if(users!=null&&users.size()>0){
			for (User user : users) {
				highlighterString = highlighter.getBestFragment(analyzer, "name",
						user.getName());
				if (highlighterString != null) {
					user.setName(highlighterString);
				}
				
				highlighterString = highlighter.getBestFragment(analyzer, "userName",
						user.getUserName());
				if (highlighterString != null) {
					user.setUserName(highlighterString);
				}
				
				highlighterString = highlighter.getBestFragment(analyzer,
						"password", user.getPassword());
				if (highlighterString != null) {
					user.setPassword(highlighterString);
				}
				
				highlighterString = highlighter.getBestFragment(analyzer, "loginEmail",
						user.getLoginEmail());
				if (highlighterString != null) {
					user.setLoginEmail(highlighterString);
				}
				
				highlighterString = highlighter.getBestFragment(analyzer, "email",
						user.getEmail());
				if (highlighterString != null) {
					user.setEmail(highlighterString);
				}

				highlighterString = highlighter.getBestFragment(analyzer,
						"identification", user.getIdentification());
				if (highlighterString != null) {
					user.setIdentification(highlighterString);
				}

				highlighterString = highlighter.getBestFragment(analyzer, "phone",
						user.getPhone());
				if (highlighterString != null) {
					user.setPhone(highlighterString);
				}
			}
		}
		
		queryResult.setSearchresult(users);
		return queryResult;
	}

}
