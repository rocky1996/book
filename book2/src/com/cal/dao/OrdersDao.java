package com.cal.dao;

import java.util.List;

import com.cal.domain.Cart;
import com.cal.domain.Orders;
import com.cal.domain.User;

public interface OrdersDao {

	//保存订单
	void addOrders(Orders orders, User user);

	//根据用户的id来查询订单
	List<Orders> findOrdersByUsersId(String UserId);

	//
	List<Orders> findOrdersByState(int i);

	Orders findOrdersById(String ordersId);

	void update(String ordersId);

}
