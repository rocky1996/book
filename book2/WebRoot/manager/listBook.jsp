<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="/manager/header.jsp" %>
		<h1>显示所有的图书</h1>
		<table width="1024" border="1">
			<tr>
				<th>书名</th>
				<th>作者</th>
				<th>描述</th>
				<th>所属分类</th>
				<th>图片</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${page.records }" var="b">
				<tr>
					<td>${b.name }</td>
					<td>${b.author }</td>
					<td>${b.description }</td>
					<td>${myfn:getCategoryName(b.category_id) }</td>
					<td>
						<a href="${pageContext.request.contextPath }/files/${b.image}">查看图片</a>
					</td>
					<td>
						<a href="">修改</a>
						<a href="">删除</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<%@include file="/commons/page.jsp" %>
	</body>
</html>