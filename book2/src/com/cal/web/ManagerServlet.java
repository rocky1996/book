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

	//��������
	private void sureOrders(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ordersId = request.getParameter("ordersId");//ȡ�ö�����id
		s.sureOrders(ordersId);
		request.setAttribute("message", "�����ɹ�!!!");
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	//�鿴������ϸ����ǰ�˵���ͬ
	private void showOrdersDetail(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ordersId = request.getParameter("ordersId");//ȡ�ö�����id
		Orders o = s.findOrdersById(ordersId);//��������ϸ��Ҫ�����;��ϸ�л�Ҫ��ѯ�������Ϣ���ĸ��û�ҲҪ��ѯ����
		request.setAttribute("o", o);
		request.getRequestDispatcher("/manager/showOrdersDetail.jsp").forward(request, response);
	}

	//��ʾ���е�δ�����Ķ���
	private void showAllOrders0(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Orders> os = s.findOrdersByState(0);
		request.setAttribute("os", os);
		request.getRequestDispatcher("/manager/showOrders.jsp").forward(request, response);
	}

	//��ʾ���е�ͼ��
	private void showAllBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pagenum = request.getParameter("pagenum");
		Page page = s.findPageRecords(pagenum);
		page.setUrl("/servlet/ManagerServlet?operation=showAllBook");
		request.setAttribute("page", page);
		request.getRequestDispatcher("/manager/listBook.jsp").forward(request, response);
	}

	//�����ǵ����ݿ�
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
//			//��������
//			List<FileItem> items = upload.parseRequest(request);
//			for(FileItem item:items){
//				if(item.isFormField()){
//					//��װ���ݵ�Jbean��
//					String fieldName = item.getFieldName();//�ֶ�������Javabean��������������ͼƬ
//					String fieldValue = item.getString(request.getCharacterEncoding());
//					BeanUtils.setProperty(book, fieldName, fieldValue);//����ͼƬ��·�������������ݶ���
//				}else{
//					//�����ļ��ϴ�
//					InputStream in = item.getInputStream();
//					String fileName = item.getName();
//					fileName = UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("//")+1);
//					
//					//���ô�ȡ��ͼƬ���ļ���
//					book.setImage(fileName);
//					
//					//���������
//					OutputStream out = new FileOutputStream(storePath+"\\"+fileName);
//					byte b[] = new byte[1024];
//					int len = -1;
//					while((len=in.read(b)) != -1){
//						out.write(b, 0, len);
//					}
//					
//					out.close();
//					in.close();
//					item.delete();//ɾ����ʱ�ļ�
//				}
//			}
//			System.out.println(book);
//		}catch (Exception e) {
//			e.printStackTrace();
//			resultPath="/message.jsp";
//			request.setAttribute("message", "������æ!!!");
//		}
//		
//		request.getRequestDispatcher(resultPath).forward(request, response);
		String resultPath = "";
		String storePath = getServletContext().getRealPath("/files");
		try {
			Book book = new Book();
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			//��������
			List<FileItem> items = upload.parseRequest(request);
			for(FileItem item:items){
				if(item.isFormField()){
					//��װ���ݵ�JavaBean��
					String fieldName = item.getFieldName();//�ֶ�������javabean��������������ͼƬ
					String fieldValue = item.getString(request.getCharacterEncoding());
					BeanUtils.setProperty(book, fieldName, fieldValue);//����ͼƬ·�����������ݶ�����
				}else{
					//�����ļ��ϴ�
					InputStream in = item.getInputStream();
					String fileName = item.getName();//   c:\dsf\a.jpg
					fileName = UUID.randomUUID()+fileName.substring(fileName.lastIndexOf("\\")+1);//a.jpg 
					//���ô�ȡ��ͼƬ�ļ���
					book.setImage(fileName);
					OutputStream out = new FileOutputStream(storePath+"\\"+fileName);
					byte b[] = new byte[1024];
					int len = -1;
					while((len=in.read(b))!=-1){
						out.write(b, 0, len);
					}
					out.close();
					in.close();
					item.delete();//ɾ����ʱ�ļ�
				}
			}
			s.addBook(book);
			//��ѯ����
			List<Category> cs = s.findAll();
			request.setAttribute("cs", cs);
			request.setAttribute("message", "<script type='text/javascript'>alert('��ӳɹ�')</script>");
		} catch (Exception e) {
			e.printStackTrace();
			resultPath = "/message.jsp";
			request.setAttribute("message", "������æ");
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

	//�����ݴ������ݿ�
	private void addCategory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Category c = WebUtil.fillBean(request, Category.class);
		s.addCategory(c);
		request.setAttribute("message", "<script type='text/javascript'>alert('��ӳɹ�!!!')</script>");
		request.getRequestDispatcher("/manager/addCategory.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
