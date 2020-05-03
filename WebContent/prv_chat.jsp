<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String nick = (String)request.getParameter("nick");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Chat Window</title>
</head>
<body>
		<div style="float: left; width: 78%; height: 1000px">
			<div id="chatBoxChild"></div>
			<form action="">
				<div>
					<textarea rows="5" style="width: 78%; float: left" id="txt_msg"
						name="msg"></textarea>
					<input type="button" value="Send" id="btnSend" onclick="doSend()">
				</div>
			</form>
		</div>
</body>
</html>