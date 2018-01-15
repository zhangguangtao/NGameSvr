<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.game.db.bean.FileUpTable"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div class="row" >


	<!-- ${fileup} -->
	<table align="center" border="1">
		<tr>
			<th align="center">id
			</td>
			<th align="center">文件名称
			</td>
			<th align="center">存储地址
			</td>
		</tr>
		<c:forEach var="fileup" items="${fileups}">

			<tr>
				<td align="center" >${fileup.id }</td>
				<td align="center">${fileup.fileName }</td>
				<td align="left"><a href="<%=basePath%>${fileup.savePath }">
						${fileup.savePath } </a></td>
			</tr>

		</c:forEach>
	</table>


	<table align="center" border="1">
		<tr>
		<c:forEach var="item" varStatus="status" begin="1" end="${pagecount }">
			
				<td align="center"><a
					href="<%=basePath%>UIManager?operation=1&page=${status.index}">
						${status.index} </a>
			    </td>
			
		</c:forEach>
		</tr>
	</table>
	

<nav aria-label="Page navigation">
  <ul class="pagination">
    <li>
      <a href="<%=basePath%>UIManager?operation=1&page=${pageRows*5 + 0}" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <!-- ${pageRows}*5 + ${item} &nbsp ${pageRows*5 + item} -->
	<c:forEach var="item" varStatus="status" begin="1" end="5">
			<c:if test="${(pageRows*5 + item) <= (pagecount)  }">
			 <li><a href="<%=basePath%>UIManager?operation=1&page=${pageRows*5 + item}">${pageRows*5 + item}</a></li>
			 </c:if>
	</c:forEach>
   
    <c:if test="${(pageRows*5 + 6) <= (pagecount)  }">
    <li>
      <a href="<%=basePath%>UIManager?operation=1&page=${pageRows*5 + 6}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
    </c:if>
  </ul>
</nav>

</div>
</div>
</body>
</html>