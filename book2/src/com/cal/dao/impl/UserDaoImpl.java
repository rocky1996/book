package com.cal.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.cal.dao.UserDao;
import com.cal.domain.User;
import com.cal.util.DBCPUtil;

public class UserDaoImpl implements UserDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource()); 
	@Override
	public void regist(User user) {
		try{
			qr.update("insert into user(id,username,password,cellphone,mobilephone,address,email) values(?,?,?,?,?,?,?)", user.getId(),user.getUsername(),user.getPassword(),user.getCellphone(),user.getMobilephone(),user.getAddress(),user.getEmail());
		}catch(Exception e){
			throw new RuntimeException();
		}		
	}
	@Override
	public User login(String username, String password) {
		try{
			return qr.query("select * from user where username=? and password=?", new BeanHandler<User>(User.class),username,password);
		}catch(Exception e){
			throw new RuntimeException();
		}
	}
}
