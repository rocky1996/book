<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/manager/header.jsp" %>
		<h1>添加分类</h1>
		<form action="${pageContext.request.contextPath }/servlet/ManagerServlet?operation=addCategory" method="post">
			<table width="66" border="1">
				<tr>
					<td>分类名称</td>
					<td>
						<input type="text" name="name"/>
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
						<textarea rows="3" cols="38" name="description"></textarea>
					</td>
				</tr>
				<tr >
					<td colspan="2">
						<input type="submit" value="提交">
					</td>
				</tr>
			</table>
		</form>
		${message }
	</body>
</html>