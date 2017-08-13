package com.cal.dao;

import java.util.List;

import com.cal.domain.Book;
import com.cal.domain.Category;
import com.cal.domain.Orders;

public interface CategoryDao {
	void addCategory(Category c);
	List<Category> findAll();
	Category findCategory(String categoryId);
}
