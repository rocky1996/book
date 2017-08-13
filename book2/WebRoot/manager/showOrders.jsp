<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/manager/header.jsp" %>
	<h1>未发货的订单</h1>
		<table border="1" width="880" align="center">
			<tr>
				<th>用户</th>
				<th>订单号</th>
				<th>金额</th>
				<th>订单状态</th>
				<th>明细</th>
			</tr>
			<c:forEach items="${os }" var="o">
				<tr align="center">
					<td>${o.user.username }</td>
					<td>${o.ordernum }</td>
					<td>${o.price }</td>
					<td>${o.state==0?'未发货':'已发货' }</td>
					<td>
						<a href="${pageContext.request.contextPath}/servlet/ManagerServlet?operation=showOrdersDetail&ordersId=${o.id}">查看详情</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>