package com.example.test.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users =  new ArrayList<>();
	private static Integer usersCount = 3;
	static {
	  users.add(new User(1,"Name1", new Date()));
	  users.add(new User(2,"Name2", new Date()));
	  users.add(new User(3,"Name3", new Date()));
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User save(User user) {
		
		if(user.getId() == null) {
			user.setId(++usersCount);
		}
		
		users.add(user);
		
		return user;
	}
	
	
	public User findOne(Integer id) {
		User userRet = null;
		for(User user: users) {
			if(user.getId() == id) {
				userRet = user;
				break;
			}
		}
	  return userRet;
	}
	
	
	
	public User deleteById(Integer id) {
		User deletedUser = null;
		Iterator itr =  users.iterator();
		
		while(itr.hasNext()) {
			User user =  (User) itr.next();
			if(user.getId() == id) {
				deletedUser = user;
				break;
			}
		}
	  return deletedUser;
	}
	
}
