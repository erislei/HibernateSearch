package org.hibernate.search.hibernate.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Entity
@Table(catalog = "hibernate_search", name = "t_user")
@Analyzer(impl = IKAnalyzer.class)
@Indexed(index = "userIndex")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	private Integer id;

	@Field(index = Index.YES, analyze = Analyze.YES)
	private String userName;

	@Field(index = Index.YES, analyze = Analyze.YES)
	@Boost(1.5f)
	private String name;

	@Field(index = Index.YES, analyze = Analyze.YES)
	private String password;

	@Field(index = Index.YES, analyze = Analyze.YES)
	private String identification;

	@Field(index = Index.YES, analyze = Analyze.YES)
	private String phone;

	@Field(index = Index.YES, analyze = Analyze.YES)
	@Boost(2f)
	private String loginEmail;

	@Field(index = Index.YES, analyze = Analyze.YES)
	private String email;

	public User() {

	}

	public User(String name, String password, String identification,
			String phone, String loginEmail, String email, String username) {
		super();
		this.name = name;
		this.password = password;
		this.identification = identification;
		this.phone = phone;
		this.email = email;
		this.loginEmail = loginEmail;
		this.userName = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("姓名：  ").append(this.name).append("\t").append("登录帐号：").
		append(this.loginEmail).append("\t").append("登录密码：").append(this.password).append("\t").append("身份证号：")
		.append(this.identification).append("\t").append("手机号：").append(this.phone).append("\n");
		return sb.toString();
	}
}
