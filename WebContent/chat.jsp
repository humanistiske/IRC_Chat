<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.nle.Helper"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>NLE Chat Bot</title>
<link rel="stylesheet" href="css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/chat.js" type="text/javascript"></script>
<script>
function onCall()
{
	document.getElementById("chatBox").onscroll = function(){
		window.onmousedown = function(){
			pauseScroll = 1;
		};
		window.onmouseup = function(){
			pauseScroll = 0;
		};
	};
	var timeOut = setInterval(read, 1000);
	if(flag==1)
	{
		setTimeout(timeOut);
	}

	var input = document.getElementById("txt_msg");
	input.addEventListener("keyup", function(event) {
		if (event.keyCode === 13) 
		{
			event.preventDefault();
			document.getElementById("btnSend").click();
		}
	}, true);
}
</script>
</head>
<body onload="onCall()">
	<div>
		<a href="logout" style="float: right">Logout</a>
	</div>
	<div>
		<div style="float: left; width: 78%; height: 1000px">
			<div id="chatBox"></div>
			<form action="">
				<div>
					<textarea rows="5" style="width: 78%; float: left" id="txt_msg"
						name="msg"></textarea>
					<input type="button" value="Send" id="btnSend" onclick="doSend()">
				</div>
			</form>
		</div>
		<div>
		<div id="userBox">
			<table id="users">
			</table>
		</div>
		<div>
			<input type="button" value="Refresh Users" onclick="chkUsers()" />
		</div>
		</div>
	</div>
</body>
</html>