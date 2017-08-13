package com.cal.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.cal.dao.CategoryDao;
import com.cal.dao.impl.CategoryDaoImpl;
import com.cal.domain.Category;
import com.cal.util.RandomUtil;

public class CategoryDaoImplTest {
	private CategoryDao dao = new CategoryDaoImpl();
	@Test
	public void testAddCategory() {
		Category c = new Category();
		c.setId(RandomUtil.random());
		c.setName("java");
		c.setDescription("dfefw");
		dao.addCategory(c);
	}

	@Test
	public void testFindAll() {
		List<Category> cs = dao.findAll();
		assertEquals(1, cs.size());
	}

}
