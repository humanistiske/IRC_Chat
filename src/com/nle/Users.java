package com.nle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Users
 */
@WebServlet("/users")
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Set<String> users = (TreeSet)request.getSession().getAttribute("users");
		
		if(users != null)
		{
			for(String u : users)
			{
				if(!u.equalsIgnoreCase((String)request.getSession().getAttribute("nick")))
				{
					response.getWriter().write("<tr><td><input type=\"radio\""
							+ "onclick=\"openChatWindow('"+u+"')\" id=\"rad_" + u + "\">"
							+ (u.contains("away") ? "<font style='color:red'>"+u+"</font>" : u) + "</input></td></tr>");	
				}
			}	
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getSession().getAttribute("chat") != null)
		{
			ChatListener chat = (ChatListener)request.getSession().getAttribute("chat");
			chat.send(chat.getOut(), "NAMES #depression");
		}
	}
}
