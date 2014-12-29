package org.hibernate.search.hibernate.example.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.search.hibernate.example.model.QueryResult;
import org.hibernate.search.hibernate.example.model.User;
import org.hibernate.search.hibernate.example.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.wltea.analyzer.lucene.IKAnalyzer;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource(name = "userServiceImpl")
	private UserService userService;

	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(value="keyword")String keyword,
							   @RequestParam(value="start")Integer start,
							   @RequestParam(value="pagesize")Integer pagesize){
		QueryResult<User> queryResult= null;
		try {
			keyword=keyword==null?"":keyword.trim();
			keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
			String[] field=new String[]{"name","password","userName","identification","phone","loginEmail"+"email"};
			queryResult = userService.query(keyword, start, pagesize, new IKAnalyzer(),field);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView("/listusers");
		modelAndView.addObject("queryResult", queryResult);
		return modelAndView;
	}
	
	
	@RequestMapping("/query/{start}/{pagesize}")
	public ModelAndView query(@PathVariable(value="start")Integer start,
							  @PathVariable(value="pagesize")Integer pagesize){
		QueryResult<User> queryResult = null;
		try {
			List<User> lists = userService.query(start, pagesize);
			queryResult= new QueryResult<User>();
			queryResult.setSearchresultsize(lists.size());
			queryResult.setSearchresult(lists);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView("list");
		modelAndView.addObject("queryResult", queryResult);
		return modelAndView;
	}
	
	
	
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String delete(@PathVariable(value="id")Integer id){
		
		try {
			userService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return "删除失败,"+e.getMessage();
		}
		
		return "删除成功!";
		
	}
	
	
	@RequestMapping(value="/save",method={RequestMethod.POST})
	@ResponseBody
	public String save(@RequestParam("name")String name,
					   @RequestParam("password")String password,
					   @RequestParam("email")String email,
					   @RequestParam("phone")String phone,
					   @RequestParam("identification")String identification) throws UnsupportedEncodingException{
		try {
			User user = new User();
			
			user.setName(name);
			user.setPassword(password);
			user.setEmail(email);
			user.setPhone(phone);
			user.setIdentification(identification);
			userService.add(user);
		} catch (Exception e) {
			e.printStackTrace();
			return "保存失败,"+e.getMessage();
		}
		
		return "保存成功!";
	}
	
	
	
	@RequestMapping(value="/modify",method={RequestMethod.POST})
	@ResponseBody
	public String modify(@RequestParam("id")String id,
						 @RequestParam("name")String name,
					     @RequestParam("password")String password,
					     @RequestParam("email")String email,
					     @RequestParam("phone")String phone,
					     @RequestParam("identification")String identification) throws UnsupportedEncodingException{
		try {
			User user = new User();
			user.setId(Integer.valueOf(id));
			user.setName(name);
			user.setPassword(password);
			user.setEmail(email);
			user.setPhone(phone);
			user.setIdentification(identification);
			userService.update(user);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "修改失败,"+e.getMessage();
		}
		return "修改成功!";
	}
	
	
	
	
	@RequestMapping("/load/{id}")
	@ResponseBody
	public User load(@PathVariable(value="id")Integer id){
		User user = userService.load(id);
		return user;
	}
	
	@RequestMapping("/list")
	public String list(){
		return "/listusers";
	}
}
