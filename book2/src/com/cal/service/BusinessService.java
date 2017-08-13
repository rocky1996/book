package com.cal.service;

import java.util.List;

import com.cal.commons.Page;
import com.cal.domain.Book;
import com.cal.domain.Cart;
import com.cal.domain.Category;
import com.cal.domain.Orders;
import com.cal.domain.User;

public interface BusinessService {
	void addCategory(Category c);
	List<Category> findAll();
	void addBook(Book book);
	
	/**
	 * 后台查询图书使用
	 */
	Page findPageRecords(String pagenum);
	Category findCategoryById(String categoryId);
	
	//按照分页就行分页的查询
	Page findPageRecords(String pagenum, String categoryId);
	Book findBookById(String bookId);
	void regist(User user);
	User login(String username, String password);
	
	//生成订单
	void addOrders(Orders orders, User user);
	List<Orders> findOrdersByUsersId(String id);
	
	//显示所有的未发货的订单
	List<Orders> findOrdersByState(int i);
	
	Orders findOrdersById(String ordersId);
	void sureOrders(String ordersId);
}
