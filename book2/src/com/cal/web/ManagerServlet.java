package com.cal.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.persistence.metamodel.SetAttribute;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cal.commons.Page;
import com.cal.domain.Book;
import com.cal.domain.Category;
import com.cal.domain.Orders;
import com.cal.service.BusinessService;
import com.cal.service.impl.BusinessServiceImpl;
import com.cal.util.WebUtil;

public class ManagerServlet extends HttpServlet {
	private BusinessService s = new BusinessServiceImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String operation = request.getParameter("operation");
		if("addCategory".equalsIgnoreCase(operation)){
			addCategory(request,response);
		}
		
		if("showAllCategory".equalsIgnoreCase(operation)){
			showAllCategory(request,response);
		}
		
		if("showAllCategoryUI".equalsIgnoreCase(operation)){
			showAllCategoryUI(request,response);
		}
		
		if("addBook".equalsIgnoreCase(operation)){
			addBook(request,response);
		}
		
		if("showAllBook".equalsIgnoreCase(operation)){
			showAllBook(request,response);
		}
		
		if("showAllOrders0".equals(operation)){
			showAllOrders0(request, response);
		}
		
		if("showOrdersDetail".equals(operation)){
			showOrdersDetail(request, response);
		}
		
		if("sureOrders".equals(operation)){
			sureOrders(request, response);
		}
		
		if("showAllOrders1".equals(operation)){
			showAllOrders1(request, response);
		}
	}

	//
	private void showAllOrders1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Orders> os = s.findOrdersByState(1);
		request.setAttribute("os", os);
		request.getRequestDispatcher("/manager/showOrders.jsp").forward(request, response);		
	}

	//订单发货
	private void sureOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ordersId = request.getParameter("ordersId");//取得订单的id
		s.sureOrders(ordersId);
		request.setAttribute("message", "发货成功!!!");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	//查看订单详细，与前端的雷同
	private void showOrdersDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ordersId = request.getParameter("ordersId");//取得订单的id
		Orders o = s.findOrdersById(ordersId);//订单的明细还要查出来;明细中还要查询出书的信息，哪个用户也要查询出来
		request.setAttribute("o", o);
		request.getRequestDispatcher("/manager/showOrdersDetail.jsp").forward(request, response);
	}

	//显示所有的未发货的订单
	private void showAllOrders0(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Orders> os = s.findOrdersByState(0);
		request.setAttribute("os", os);
		request.getRequestDispatcher("/manager/showOrders.jsp").forward(request, response);
	}

	//显示所有的图书
	private void showAllBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");
		Page page = s.findPageRecords(pagenum);
		page.setUrl("/servlet/ManagerServlet?operation=showAllBook");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manager/listBook.jsp").forward(request, response);
	}

	//添加书记到数据库
	private void addBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		String resultPath = "";
//		String storePath = getServletContext().getRealPath("/files");
//		try {
//			
//			Book book = new Book();
//			
//			DiskFileItemFactory factory = new DiskFileItemFactory();
//			ServletFileUpload upload = new ServletFileUpload(factory);
//			
//			//解析请求
//			List<FileItem> items = upload.parseRequest(request);
//			for(FileItem item:items){
//				if(item.isFormField()){
//					//封装数据到Jbean中
//					String fieldName = item.getFieldName();//字段名，即Javabean的属性名，除了图片
//					String fieldValue = item.getString(request.getCharacterEncoding());
//					BeanUtils.setProperty(book, fieldName, fieldValue);//除了图片和路径，其他的数据都有
//				}else{
//					//处理文件上传
//					InputStream in = item.getInputStream();
//					String fileName = item.getName();
//					fileName = UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("//")+1);
//					
//					//设置存取的图片的文件名
//					book.setImage(fileName);
//					
//					//构建输出流
//					OutputStream out = new FileOutputStream(storePath+"\\"+fileName);
//					byte b[] = new byte[1024];
//					int len = -1;
//					while((len=in.read(b)) != -1){
//						out.write(b, 0, len);
//					}
//					
//					out.close();
//					in.close();
//					item.delete();//删除临时文件
//				}
//			}
//			System.out.println(book);
//		}catch (Exception e) {
//			e.printStackTrace();
//			resultPath="/message.jsp";
//			request.setAttribute("message", "服务器忙!!!");
//		}
//		
//		request.getRequestDispatcher(resultPath).forward(request, response);
		String resultPath = "";
		String storePath = getServletContext().getRealPath("/files");
		try {
			Book book = new Book();
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解析请求
			List<FileItem> items = upload.parseRequest(request);
			for(FileItem item:items){
				if(item.isFormField()){
					//封装数据到JavaBean中
					String fieldName = item.getFieldName();//字段名，即javabean的属性名，除了图片
					String fieldValue = item.getString(request.getCharacterEncoding());
					BeanUtils.setProperty(book, fieldName, fieldValue);//除了图片路径，其他数据都有了
				}else{
					//处理文件上传
					InputStream in = item.getInputStream();
					String fileName = item.getName();//   c:\dsf\a.jpg
					fileName = UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("\\")+1);//a.jpg 
					//设置存取的图片文件名
					book.setImage(fileName);
					OutputStream out = new FileOutputStream(storePath+"\\"+fileName);
					byte b[] = new byte[1024];
					int len = -1;
					while((len=in.read(b))!=-1){
						out.write(b, 0, len);
					}
					out.close();
					in.close();
					item.delete();//删除临时文件
				}
			}
			s.addBook(book);
			//查询分类
			List<Category> cs = s.findAll();
			request.setAttribute("cs", cs);
			request.setAttribute("message", "<script type='text/javascript'>alert('添加成功')</script>");
		} catch (Exception e) {
			e.printStackTrace();
			resultPath = "/message.jsp";
			request.setAttribute("message", "服务器忙");
		}
		request.getRequestDispatcher("/manager/addBook.jsp").forward(request, response);
	}

	private void showAllCategoryUI(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAll();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manager/addBook.jsp").forward(request, response);
	}

	private void showAllCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Category> cs = s.findAll();
		request.setAttribute("cs", cs);
		request.getRequestDispatcher("/manager/listCategory.jsp").forward(request, response);
		
	}

	//将数据存在数据库
	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Category c = WebUtil.fillBean(request, Category.class);
		s.addCategory(c);
		request.setAttribute("message", "<script type='text/javascript'>alert('添加成功!!!')</script>");
		request.getRequestDispatcher("/manager/addCategory.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
