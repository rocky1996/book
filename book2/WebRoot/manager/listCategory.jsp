<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/manager/header.jsp" %>
		<h1>分类列表</h1>
		<c:if test="${empty cs}">
			没有分类，请先分类
		</c:if>
		<c:if test="${!empty cs }">
			<table width="90%" border="1">
				<tr>
					<th>选择</th>
					<th>分类</th>
					<th>描述</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${cs }" var="c">
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${c.id }">
						</td>
						<td>
							${c.name }
						</td>
						<td>
							${c.description }
						</td>
						<td>
							<a href="">修改</a>
							<a href="">删除</a>						 
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</body>
</html>