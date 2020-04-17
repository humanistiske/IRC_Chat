<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	function chkEnter(obj)
	{
		var input = obj;
		
		input.addEventListener("keyup", function(event) {
			event.preventDefault();
		  	if (event.keyCode === 13) 
			{
		   		doSend();
		  	}
		}, true);
	}
</script>
</head>
<body onload="onCall()">
	<a href="logout" style="float:right">Logout</a>
	<div id="chatBox"></div>
	<div>
		<form action="">
			<textarea rows="5" style="width: 100%" onfocus="chkEnter(this)" name="msg"></textarea>
			<input type="button" value="Send" onclick="doSend()">
		</form>
	</div>
</body>
</html>