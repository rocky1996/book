<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/client/header.jsp" %>
	<h1>新用户注册</h1>
		<form action="${pageContext.request.contextPath }/servlet/ClientServlet?operation=regist" method="post">
			<table border="1" width="660">
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="username"/></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td>电话:</td>
					<td><input type="text" name="cellphone"/></td>
				</tr>
				<tr>
					<td>手机:</td>
					<td><input type="text" name="mobilephone"/></td>
				</tr>
				<tr>
					<td>地址:</td>
					<td><input type="text" name="address"/></td>
				</tr>
				<tr>
					<td>邮箱:</td>
					<td><input type="text" name="email"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="提交"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>