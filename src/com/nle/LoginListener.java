package com.nle;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class LoginListener implements HttpSessionAttributeListener
{
	private ChatListener chat = null;
	private String channel = null;
	
	@Override
	public void attributeAdded(HttpSessionBindingEvent e) 
	{
		if(e.getName().equalsIgnoreCase("channel"))
			channel = (String)e.getValue();
		
		if(e.getValue() instanceof ChatListener)
			chat = (ChatListener)e.getValue();
		
		if(e.getName().equalsIgnoreCase("chat") && chat != null)
		{
			chat.start();
		}
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent e) 
	{
		if(e.getValue() instanceof ChatListener)
			chat = (ChatListener)e.getValue();
		
		if(e.getName().equalsIgnoreCase("chat") && chat != null)
		{
			chat.interrupt();
		}
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
