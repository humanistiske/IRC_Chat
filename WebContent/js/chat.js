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
function openChatWindow(nick)
{
	var url = 'prv_chat.jsp?nick='+nick;
	var varWindow = window.open(url,'name','width=600,height=400');
	varWindow.onbeforeunload = function(){
		varWindow.opener.document.getElementById('rad_'+nick).checked = false;
	};
}