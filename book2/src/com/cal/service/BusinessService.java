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
	 * ��̨��ѯͼ��ʹ��
	 */
	Page findPageRecords(String pagenum);
	Category findCategoryById(String categoryId);
	
	//���շ�ҳ���з�ҳ�Ĳ�ѯ
	Page findPageRecords(String pagenum, String categoryId);
	Book findBookById(String bookId);
	void regist(User user);
	User login(String username, String password);
	
	//���ɶ���
	void addOrders(Orders orders, User user);
	List<Orders> findOrdersByUsersId(String id);
	
	//��ʾ���е�δ�����Ķ���
	List<Orders> findOrdersByState(int i);
	
	Orders findOrdersById(String ordersId);
	void sureOrders(String ordersId);
}
