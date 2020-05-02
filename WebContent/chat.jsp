<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.nle.Helper"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>NLE Chat Bot</title>
<style type="text/css">
#chatBox{
	overflow: auto;
	position: relative;
	height: 600px;
	width: 100%;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	var flag = 0;
	var pauseScroll = 0;
	var chatSize = 0;
	
	function read()
	{
		var req = new XMLHttpRequest();
		req.onreadystatechange = function(){
			if(this.status==200 && this.readyState==4)
			{
				console.log(this.responseText);
				document.getElementById("chatBox").innerHTML += this.responseText; 
			}
			else
			{
				flag = 1;
			}
		};

		if(pauseScroll==0 && document.getElementById("chatBox").textContent.length > chatSize)
		{
			var objDiv = document.getElementById("chatBox");
			$("#chatBox").animate({ scrollTop: objDiv.scrollHeight}, 1000);
			chatSize = document.getElementById("chatBox").textContent.length;
		}
		
		req.open("GET", "controller", true);
		req.send();
	}
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
	function doSend()
	{
		var xhttp = new XMLHttpRequest();
		var data = "msg="+document.getElementsByName('msg')[0].value;
		document.getElementsByName('msg')[0].value = "";
		xhttp.onreadystatechange = function(){
			if(xhttp.readyState==4)
			{
				console.log("Message Send"+data);
			}
		};
		xhttp.open("POST", "controller", true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send(data);
	}
	function chkUsers()
	{
		var request = new XMLHttpRequest();
		request.onreadystatechange = function(){
			if(request.readyState==4)
			{
				console.log("request users");
			}	
		};
		request.open("POST", "users", true);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.send();

		var response = new XMLHttpRequest();
		response.onreadystatechange = function(){
			if(response.status==200 && response.readyState==4)
			{
				console.log("works");
				console.log(response.responseText);
				document.getElementById("users").innerHTML = response.responseText;
			}	
		};
		response.open("GET", "users", true);
		response.send();
	}
</script>
</head>
<body onload="onCall()">
	<a href="logout" style="float:right">Logout</a>
	<div id="chatBox"></div>
	<form action="">
		<div>
			<textarea rows="5" style="width: 50%; float: left" id="txt_msg" name="msg"></textarea>
			<input type="button" value="Send" id="btnSend" onclick="doSend()">
			<!-- <textarea rows="5" style="width: 50%; float:left" onfocus="chkEnterCommand(this)" name="cmd"></textarea>
			<input type="button" value="Command" onclick="doSendCmd()"> -->
		</div>
	</form>
		<div style="float: right; border: 1cm;">
			<ul id="users">
			</ul>
			<input type="button" value="Check" onclick="chkUsers()"/>
		</div>

</body>
</html>