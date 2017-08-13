<%@page contentType="text/html; charset=UTF-8" %>
<html>
	<body>
		<jsp:forward page="/servlet/ClientServlet">
			<jsp:param value="showIndexCategory" name="operation"></jsp:param>
		</jsp:forward>
	</body>
</html>
