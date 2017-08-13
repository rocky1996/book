package com.cal.service.impl;

import java.util.List;

import com.cal.commons.Page;
import com.cal.dao.BookDao;
import com.cal.dao.CategoryDao;
import com.cal.dao.OrdersDao;
import com.cal.dao.UserDao;
import com.cal.dao.impl.BookDaoImpl;
import com.cal.dao.impl.CategoryDaoImpl;
import com.cal.dao.impl.OrdersDaoImpl;
import com.cal.dao.impl.UserDaoImpl;
import com.cal.domain.Book;
import com.cal.domain.Cart;
import com.cal.domain.Category;
import com.cal.domain.Orders;
import com.cal.domain.OrdersItem;
import com.cal.domain.User;
import com.cal.service.BusinessService;
import com.cal.util.RandomUtil;

public class BusinessServiceImpl implements BusinessService {
	private CategoryDao dao = new CategoryDaoImpl();
	private BookDao dao1 = new BookDaoImpl();
	private UserDao dao2 = new UserDaoImpl();
	private OrdersDao dao3 = new OrdersDaoImpl();
	@Override
	public void addCategory(Category c) {
		c.setId(RandomUtil.random());
		dao.addCategory(c);
	}

	@Override
	public List<Category> findAll() {
		return dao.findAll();
	}

	@Override
	public void addBook(Book book) {
		book.setId(RandomUtil.random());
		dao1.addBook(book);
	}

	@Override
	public Page findPageRecords(String pagenum) {
		int num=1;//默认页
		if(pagenum != null && !"".equals(pagenum.trim().toString())){
			num = Integer.parseInt(pagenum);
		}
		
		int totalrecords =  dao1.getTotalRecord();
		
		Page page = new Page(num, totalrecords);
		List records = dao1.findPageBooks(page.getStartindex(), page.getPagesize());
		page.setRecords(records);
		return page;
	}

	@Override
	public Category findCategoryById(String categoryId) {
		
		return dao.findCategory(categoryId);
	}

	@Override
	public Page findPageRecords(String pagenum, String categoryId) {
		int num=1;//默认页
		if(pagenum != null && !"".equals(pagenum.trim().toString())){
			num = Integer.parseInt(pagenum);
		}
		
		int totalrecords =  dao1.getTotalRecord(categoryId);
		
		Page page = new Page(num, totalrecords);
		List records = dao1.findPageBooks(page.getStartindex(), page.getPagesize(), categoryId);
		page.setRecords(records);
		return page;
	}

	@Override
	public Book findBookById(String bookId) {
		return dao1.findBookById(bookId);
	}

	@Override
	public void regist(User user) {
		user.setId(RandomUtil.random());
		dao2.regist(user);	
	}

	@Override
	public User login(String username, String password) {
		return dao2.login(username,password);
	}

	@Override
	public void addOrders(Orders orders, User user) {
		orders.setId(RandomUtil.random());
		orders.setOrdernum(RandomUtil.orderNum());
		
		//给购物项增添id
		List<OrdersItem> items = orders.getItems();
		for(OrdersItem item:items){
			item.setId(RandomUtil.random());
			
		}
		dao3.addOrders(orders, user);
	}

	@Override
	public List<Orders> findOrdersByUsersId(String id) {
		return dao3.findOrdersByUsersId(id);
	}

	@Override
	public List<Orders> findOrdersByState(int i) {
		return dao3.findOrdersByState(i);
	}

	@Override
	public Orders findOrdersById(String ordersId) {
		return dao3.findOrdersById(ordersId);
	}

	@Override
	public void sureOrders(String ordersId) {
		dao3.update(ordersId);		
	}

}
