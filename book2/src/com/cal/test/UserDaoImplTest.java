package com.cal.test;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.cal.dao.UserDao;
import com.cal.dao.impl.UserDaoImpl;
import com.cal.domain.User;
import com.cal.util.RandomUtil;

public class UserDaoImplTest {
	private UserDao dao = new UserDaoImpl();
	@Test
	public void testRegist() {
		User u = new User();
		u.setId(RandomUtil.random());
		u.setUsername("fdefe");
		u.setPassword("fewe");
		u.setCellphone("3412");
		u.setMobilephone("42534");
		u.setAddress("435454");
		u.setEmail("fewefe");
		dao.regist(u);
	}

	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}

}
