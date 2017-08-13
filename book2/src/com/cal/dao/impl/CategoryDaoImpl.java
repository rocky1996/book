package com.cal.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cal.dao.CategoryDao;
import com.cal.domain.Book;
import com.cal.domain.Category;
import com.cal.util.DBCPUtil;

public class CategoryDaoImpl implements CategoryDao {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	@Override
	public void addCategory(Category c) {
		try{
			qr.update("insert into category(id,name,description) values(?,?,?)", c.getId(),c.getName(),c.getDescription());
		}catch(Exception e){
			throw new RuntimeException();
		}
	}

	@Override
	public List<Category> findAll() {
		try{
			return qr.query("select * from category", new BeanListHandler<Category>(Category.class));
		}catch(Exception e){
			throw new RuntimeException();
		}
	}

	@Override
	public Category findCategory(String categoryId) {
		try{
			return qr.query("select * from category where id=?", new BeanHandler<Category>(Category.class),categoryId);
		}catch(Exception e){
			throw new RuntimeException();
		}
	}
}
