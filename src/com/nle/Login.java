package com.nle;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet 
{
	private ChatListener chat = null;
	private static final long serialVersionUID = 1L;
	
	public void init()
	{
		chat = new ChatListener();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String nick = request.getParameter("nick");
		String channel = request.getParameter("channel");
		request.getServletContext().setAttribute("chatSocket", chat);
		request.getSession().setAttribute("nick", nick);
		request.getSession().setAttribute("channel", channel);
		chat.join(nick, channel);
		request.getSession().setAttribute("chat", chat);
		response.sendRedirect("chat.jsp");
	}
}
