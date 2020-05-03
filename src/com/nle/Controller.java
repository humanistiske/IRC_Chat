package com.nle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
	private Set<String> users = new TreeSet();
	private String search = "";
	private int totalUsers = 0;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String channel = (String)session.getAttribute("channel");
		String nick = ((String)session.getAttribute("nick"));		
		search = nick + " = " + channel + " :";
//		System.out.println("Names:"+line.contains(search));
//		System.out.println(line);
		if(session.getAttribute("users") != null)
		{
			users = (TreeSet)session.getAttribute("users");
		}
		totalUsers = users.size();
		
		file = ApplicationProperties.FILE_PATH + 
						channel.replace("#", "") + "_" +
						nick + ".log";
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		LocalDateTime timestamp = null;
		
		while((line = br.readLine()) != null)
		{
			if(line.contains(search))
			{
				line = line.substring(line.indexOf(search)+search.length());
				users.addAll(Arrays.asList(line.split("[ ,+@]+")));
				if(users.size() > totalUsers)
				{
					session.setAttribute("users", users);
					System.out.println("Online:"+users);	
				}	
			}
			
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
							response.getWriter().write("<p id='chatMsg'><b>"+line.substring(0, line.indexOf("!"))+"</b> &nbsp"
									+ line.substring(line.indexOf(channel+" :")+(channel+" :").length())+"</p>");
						}
						else
						{
							response.getWriter().write("<p id='chatMsg'><b>"+line.substring(0, line.indexOf("!"))+"</b> &nbsp"
									+ line.substring(line.indexOf(nick+" :")+(nick+" :").length())+"</p>");
						}			
						session.setAttribute("timestamp", timestamp);
					}
					else
					{
//						System.out.println(timestamp);
						if(timestamp.isAfter((LocalDateTime)session.getAttribute("timestamp")))
						{
							if(line.contains(channel))
							{
								response.getWriter().write("<p id='chatMsg'><b>"+line.substring(0, line.indexOf("!"))+"</b> &nbsp"
										+ line.substring(line.indexOf(channel+" :")+(channel+" :").length())+"</p>");
							}
							else
							{
								response.getWriter().write("<p id='chatMsg'><b>"+line.substring(0, line.indexOf("!"))+"</b> &nbsp"
										+ line.substring(line.indexOf(nick+" :")+(nick+" :").length())+"</p>");
							}
							
							session.setAttribute("timestamp", timestamp);
						}
					}
					response.getWriter().flush();
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
