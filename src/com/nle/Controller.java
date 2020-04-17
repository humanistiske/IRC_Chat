package com.nle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String file = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String channel = (String)session.getAttribute("channel");
		String nick = ((String)session.getAttribute("nick"));
		
		
		file = ApplicationProperties.FILE_PATH + 
						channel.replace("#", "") + "_" +
						nick + ".log";
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		LocalDateTime timestamp = null;
		JSONObject json = new JSONObject();
		
		while((line = br.readLine()) != null)
		{
			if(line.contains("PRIVMSG"))
			{
				timestamp = LocalDateTime.parse(line.substring(0, line.indexOf(">>>>")));
				line = line.substring(line.indexOf(">>>> :")+6);
				try
				{
					if(session.getAttribute("timestamp") == null)
					{
						if(line.contains(channel))
						{
							json.put("from", line.substring(0, line.indexOf("!")));
							json.put("msg", line.substring(line.indexOf(channel+" :")+(channel+" :").length()));
							response.getWriter().write("<p id='chatMsg'><b>"+line.substring(0, line.indexOf("!"))+"</b> &nbsp"
									+ line.substring(line.indexOf(channel+" :")+(channel+" :").length())+"</p>");
//							response.getWriter().print(json);
						}
						else
						{

							json.put("from", line.substring(0, line.indexOf("!")));
							json.put("msg", line.substring(line.indexOf(nick+" :")+(nick+" :").length()));
							response.getWriter().write("<p id='chatMsg'><b>"+line.substring(0, line.indexOf("!"))+"</b> &nbsp"
									+ line.substring(line.indexOf(nick+" :")+(nick+" :").length())+"</p>");
//							response.getWriter().print(json);
						}
						session.setAttribute("timestamp", timestamp);
					}
					else
					{
						if(timestamp.isAfter((LocalDateTime)session.getAttribute("timestamp")))
						{
							if(line.contains(channel))
							{
								json.put("from", line.substring(0, line.indexOf("!")));
								json.put("msg", line.substring(line.indexOf(channel+" :")+(channel+" :").length()));
								response.getWriter().write("<p id='chatMsg'><b>"+line.substring(0, line.indexOf("!"))+"</b> &nbsp"
										+ line.substring(line.indexOf(channel+" :")+(channel+" :").length())+"</p>");
//								response.getWriter().print(json);
							}
							else
							{
								json.put("from", line.substring(0, line.indexOf("!")));
								json.put("msg", line.substring(line.indexOf(nick+" :")+(nick+" :").length()));
								response.getWriter().write("<p id='chatMsg'><b>"+line.substring(0, line.indexOf("!"))+"</b> &nbsp"
										+ line.substring(line.indexOf(nick+" :")+(nick+" :").length())+"</p>");
//								response.getWriter().print(json);
							}
							session.setAttribute("timestamp", timestamp);
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		br.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		if(request.getSession().getAttribute("chat") != null)
		{
			ChatListener chat = (ChatListener)request.getSession().getAttribute("chat");
			chat.send(chat.getOut(), "PRIVMSG #depression " + request.getParameter("msg"));
			chat.send(chat.getfOut(), LocalDateTime.now() + ">>>> " + ":You! PRIVMSG #depression :" + request.getParameter("msg"));
		}
	}
}
