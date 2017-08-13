package com.cal.dao;

import java.util.List;

import com.cal.domain.Book;

public interface BookDao {
	void addBook(Book book);
	int getTotalRecord();//��ѯ���еļ�¼����
	List<Book> findPageBooks(int startIndex,int pagesize);
	int getTotalRecord(String categoryId);
	List findPageBooks(int startindex, int pagesize, String categoryId);
	Book findBookById(String bookId);
}
