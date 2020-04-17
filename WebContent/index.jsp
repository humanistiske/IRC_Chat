<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	if(session.getAttribute("chat") != null)
	{
		session.removeAttribute("nick");
		session.removeAttribute("channel");
		session.removeAttribute("chat");
	}
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="login" method="post">
		<label for="nick" >Nick Name:</label>
		<input type="text" placeholder="John" name="nick"><br>
		<label for="channel" >Channel Name:</label>
		<input type="text" placeholder="#Channel" name="channel"><br>
		<input type="submit" value="Login"/>
	</form>
</body>
</html>