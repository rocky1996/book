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
		String ordersId = request.getParameter("ordersId");//ȡ�ö�����id
		Orders o = s.findOrdersById(ordersId);//��������ϸ��Ҫ�����;��ϸ�л�Ҫ��ѯ�������Ϣ
		request.setAttribute("o", o);
		request.getRequestDispatcher("/client/showOrdersDetail.jsp").forward(request, response);
	}

	//�����û���id����ѯ�Լ��Ķ���
	private void showUserOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message", "���ȵ�¼������");
			request.getRequestDispatcher("/client/message.jsp").forward(request, response);
			return;
		}
		
		List<Orders> os = s.findOrdersByUsersId(user.getId());//��ѯĳ���û������ж���
		request.setAttribute("os", os);
		request.getRequestDispatcher("/client/listOrders.jsp").forward(request, response);
	}

	//ע�����˵���Ϣ
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().invalidate();
		request.getRequestDispatcher("/").forward(request, response);		
	}

	//���ɶ������Ѷ�����Ϣ�浽���ݿ���
	private void genOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//�ж��û��Ƿ��¼
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message", "���ȵ�¼������");
			request.getRequestDispatcher("/client/message.jsp").forward(request, response);
			return;
		}
		
		//ȡ�����ﳵCart Map<String,CartItem>
		Cart cart = (Cart)session.getAttribute("cart");
		//��cart�е�����Ū��ģ����ȥ:������Ͷ�����
		Orders orders = new Orders();
		orders.setNum(cart.getNum());
		orders.setPrice(cart.getPrice());
		
		//��Ū������
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
		request.setAttribute("message", "���ɶ����ɹ�������");
		request.getRequestDispatcher("/client/message.jsp").forward(request, response);
	}

	//�û���¼
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = s.login(username,password);
		if(user==null){
			request.setAttribute("message", "�û������������!!!");
			request.getRequestDispatcher("/client/message.jsp").forward(request, response);
		}else{
			request.getSession().setAttribute("user", user);
			response.sendRedirect(request.getContextPath());
		}
	}

	//ע���û�
	private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = WebUtil.fillBean(request, User.class);
		s.regist(user);
		request.setAttribute("message", "ע��ɹ�������");
		request.getRequestDispatcher("/client/message.jsp").forward(request, response);
	}

	private void buyBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//��ȡ���id
		String bookId = request.getParameter("bookId");
		
		//��ȡҪ�����
		Book book = s.findBookById(bookId);
		
		//��session��ȡ�����ﳵ
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		
		//û�У��򴴽����ﳵ���ŵ�session��
		if(cart==null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		
		//����ŵ����ﳵ��
		cart.addBook(book);
		
		//��ʾ����ɹ�
		request.setAttribute("message", "����ɹ�������");
		request.getRequestDispatcher("/client/message.jsp").forward(request, response);
	}

	//���շ�ҳ�����鼮��ҳ��ѯ
	private void showCategoryBooks(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");
		String categoryId = request.getParameter("categoryId");
		Page page = s.findPageRecords(pagenum, categoryId);
		page.setUrl("/servlet/ClientServlet?operation=showCategoryBooks"+categoryId);
		request.setAttribute("page", page);
		request.getRequestDispatcher("/client/welcome.jsp").forward(request, response);
	}

	//��ѯ���еķ��࣬��װ�� �Ա�����ǰ����ʾ
	//��ѯ���е��鼮����Ҫ��ҳ
	private void showIndexCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAll();
		request.getSession().setAttribute("cs", cs);
		
		//��ѯ���е��鼮����Ҫ��ҳ
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
