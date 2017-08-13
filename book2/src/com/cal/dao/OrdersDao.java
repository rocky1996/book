package com.cal.dao;

import java.util.List;

import com.cal.domain.Cart;
import com.cal.domain.Orders;
import com.cal.domain.User;

public interface OrdersDao {

	//���涩��
	void addOrders(Orders orders, User user);

	//�����û���id����ѯ����
	List<Orders> findOrdersByUsersId(String UserId);

	//
	List<Orders> findOrdersByState(int i);

	Orders findOrdersById(String ordersId);

	void update(String ordersId);

}
