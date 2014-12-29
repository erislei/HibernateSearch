package org.hibernate.search.hibernate.example.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.hibernate.search.hibernate.example.dao.UserDao;
import org.hibernate.search.hibernate.example.model.QueryResult;
import org.hibernate.search.hibernate.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager = "hibernate4TransactionManager", defaultRollback = false)
@Transactional
public class UserDaoImplTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Resource(name = "userDaoImpl")
	private UserDao userDao;

	@Test
	public void query() {
		List<User> list = userDao.query(5, 5);
		for (User User : list) {
			System.out.println(User.getName());
		}
	}

	@Test
	public void delete() {
		userDao.delete(11);
	}

	@Test
	public void save() throws IOException {
		File file = new File("e:\\12306.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line = null;
		while((line=reader.readLine())!=null){
			String[] users = line.split("----");
			//name,password,identification, phone, loginEmail,email,username
			User user = new User(users[2], users[1], users[3], users[5], users[0],users[6],users[4]);
			userDao.add(user);
		}	
		if(reader!=null){
			reader.close();
		}
	}

	@Test
	public void search(){
		int start=0;
		int pagesize=5;
		Analyzer analyzer=new IKAnalyzer();
		String[] field=new String[]{"name","password","userName","identification","phone","loginEmail"+"email"};
		QueryResult<User> queryResult= null;
		try {
			queryResult = userDao.query("郭超南", start, pagesize, analyzer, field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("共检索到["+queryResult.getSearchresultsize()+"]条记录!");
		
		for (User user : queryResult.getSearchresult()) {
			System.out.println(user);
		}
	}

}
