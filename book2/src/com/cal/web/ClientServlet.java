package com.cal.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cal.commons.Page;
import com.cal.domain.Book;
import com.cal.domain.Cart;
import com.cal.domain.CartItem;
import com.cal.domain.Category;
import com.cal.domain.Orders;
import com.cal.domain.OrdersItem;
import com.cal.domain.User;
import com.cal.service.BusinessService;
import com.cal.service.impl.BusinessServiceImpl;
import com.cal.util.WebUtil;

public class ClientServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String operation = request.getParameter("operation");
		if("showIndexCategory".equals(operation)){
			showIndexCategory(request, response);	
		}
		
		if("showCategoryBooks".equals(operation)){
			showCategoryBooks(request, response);
		}
		
		if("buyBook".equals(operation)){
			buyBook(request, response);
		}
		
		if("regist".equals(operation)){
			regist(request, response);
		}
		
		if("login".equals(operation)){
			login(request, response);
		}
		
		if("genOrders".equals(operation)){
			genOrders(request, response);
		}
		
		if("logout".equals(operation)){
			logout(request, response);
		}
		
		if("showUserOrders".equals(operation)){
			showUserOrders(request, response);
		}
		
		if("showOrdersDetail".equals(operation)){
			showOrdersDetail(request, response);
		}
	}
	
	
	private void showOrdersDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ordersId = request.getParameter("ordersId");//取得订单的id
		Orders o = s.findOrdersById(ordersId);//订单的明细还要查出来;明细中还要查询出书的信息
		request.setAttribute("o", o);
		request.getRequestDispatcher("/client/showOrdersDetail.jsp").forward(request, response);
	}

	//根据用户的id来查询自己的订单
	private void showUserOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message", "请先登录！！！");
			request.getRequestDispatcher("/client/message.jsp").forward(request, response);
			return;
		}
		
		List<Orders> os = s.findOrdersByUsersId(user.getId());//查询某个用户的所有订单
		request.setAttribute("os", os);
		request.getRequestDispatcher("/client/listOrders.jsp").forward(request, response);
	}

	//注销个人的信息
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().invalidate();
		request.getRequestDispatcher("/").forward(request, response);		
	}

	//生成订单，把订单信息存到数据库中
	private void genOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//判断用户是否登录
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message", "请先登录！！！");
			request.getRequestDispatcher("/client/message.jsp").forward(request, response);
			return;
		}
		
		//取出购物车Cart Map<String,CartItem>
		Cart cart = (Cart)session.getAttribute("cart");
		//把cart中的数据弄到模型中去:订单项和订单项
		Orders orders = new Orders();
		orders.setNum(cart.getNum());
		orders.setPrice(cart.getPrice());
		
		//在弄购物项
		List<OrdersItem> orderItems = new ArrayList<OrdersItem>();
		for(Map.Entry<String, CartItem> item:cart.getItems().entrySet()){
			CartItem i = item.getValue();
			OrdersItem orderItem = new OrdersItem();
			orderItem.setNum(i.getNum());
			orderItem.setPrice(i.getPrice());
			orderItem.setBook(i.getBook());
			orderItems.add(orderItem);
		}
		    
		orders.setItems(orderItems);
		s.addOrders(orders,user);
		request.setAttribute("message", "生成订单成功！！！");
		request.getRequestDispatcher("/client/message.jsp").forward(request, response);
	}

	//用户登录
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = s.login(username,password);
		if(user==null){
			request.setAttribute("message", "用户名或密码错误!!!");
			request.getRequestDispatcher("/client/message.jsp").forward(request, response);
		}else{
			request.getSession().setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		}
	}

	//注册用户
	private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = WebUtil.fillBean(request, User.class);
		s.regist(user);
		request.setAttribute("message", "注册成功！！！");
		request.getRequestDispatcher("/client/message.jsp").forward(request, response);
	}

	private void buyBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//获取书的id
		String bookId = request.getParameter("bookId");
		
		//获取要买的书
		Book book = s.findBookById(bookId);
		
		//从session中取出购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		
		//没有，则创建购物车并放到session中
		if(cart==null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		
		//把书放到购物车中
		cart.addBook(book);
		
		//提示购买成功
		request.setAttribute("message", "购买成功！！！");
		request.getRequestDispatcher("/client/message.jsp").forward(request, response);
	}

	//按照分页进行书籍分页查询
	private void showCategoryBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");
		String categoryId = request.getParameter("categoryId");
		Page page = s.findPageRecords(pagenum, categoryId);
		page.setUrl("/servlet/ClientServlet?operation=showCategoryBooks"+categoryId);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/client/welcome.jsp").forward(request, response);
	}

	//查询所有的分类，封装后， 以便于在前端显示
	//查询所有的书籍，还要分页
	private void showIndexCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAll();
		request.getSession().setAttribute("cs", cs);
		
		//查询所有的书籍，还要分页
		String pagenum = request.getParameter("pagenum");
		Page page = s.findPageRecords(pagenum);
		page.setUrl("/servlet/ClientServlet?operation=showIndexCategory");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/client/welcome.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
