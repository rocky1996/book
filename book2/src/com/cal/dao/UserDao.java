package com.cal.dao;

import com.cal.domain.User;

public interface UserDao {

	void regist(User user);

	User login(String username, String password);

}
