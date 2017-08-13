package com.cal.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cal.dao.BookDao;
import com.cal.dao.impl.BookDaoImpl;
import com.cal.domain.Book;
import com.cal.util.RandomUtil;

public class BookDaoImplTest {
	private BookDao dao = new BookDaoImpl();
	@Test
	public void testAddBook() {
		Book book = new Book();
		book.setId(RandomUtil.random());
		book.setName("king");
		book.setAuthor("poing");
		book.setPrice((float) 12.00);
		book.setImage("");
		book.setDescription("cccccccccccccc");
		book.setCategory_id("263ade11-b5d9-4a26-b7f3-75a895983ff0");
		dao.addBook(book);
	}

}
