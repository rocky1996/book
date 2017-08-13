package com.cal.web;

import com.cal.domain.Category;
import com.cal.service.BusinessService;
import com.cal.service.impl.BusinessServiceImpl;

public class MyFunction {
	private static BusinessService s = new BusinessServiceImpl();
	public static String getCategoryName(String categoryId){
		Category c = s.findCategoryById(categoryId);
		if(c != null){
			return c.getName();
		}
		return "";
	}
}
