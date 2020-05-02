package com.nle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class ChatListener extends Thread implements ApplicationProperties
{
	private InputStreamReader inStream;
	private OutputStreamWriter outStream;
	private BufferedWriter out;
	private BufferedReader in;
	private String channel;
	private String nick;
	private BufferedWriter fOut;
	
	public BufferedWriter getOut() {
		return out;
	}
	
	public BufferedWriter getfOut() {
		return fOut;
	}
	
	ChatListener()
	{
		Socket s = null;
		
		try 
		{
			s = new Socket("irc.healthfulchat.org", 6667);
			this.inStream = new InputStreamReader(s.getInputStream());
			this.outStream = new OutputStreamWriter(s.getOutputStream());
			this.out = new BufferedWriter(this.outStream);
			this.in = new BufferedReader(this.inStream);
			System.out.println(">>>>>>>>>>>> Writer and reader established");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void send(BufferedWriter out, String msg)
	{
		try 
		{
			out.write(msg+"\r\n");
//			System.out.println("From Client: " + msg);
			out.flush();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void join(String nick, String channel)
	{
		send(out, "NICK "+nick);
		send(out, "USER bot 0 * :"+nick);
		this.channel = channel;
		this.nick = nick;
		try 
		{
			fOut = new BufferedWriter(new FileWriter(FILE_PATH + channel.replace("#", "") + "_" + nick + ".log", true));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	 
	@Override
	public void run()
	{
		String line = null;
		
		try 
		{
			while((line = in.readLine()) != null)
			{
//				System.out.println(line);
				if(line.contains("PING"))
				{
					line = line.replace("PING", "PONG");
					send(out, line);
				}
				else if(line.contains("MODE"))
				{
					send(out, "JOIN "+channel);
				}
				else if(line.contains(channel) || line.contains("PRIVMSG"))
				{
					send(fOut, LocalDateTime.now() + ">>>> " + line.replaceAll("[\u0000-\u001F]+[\\d]+", "").replaceAll("[\u0000-\u001F]+", ""));
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				fOut.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void receive(String channel)
	{
		String line = null;
		
		try 
		{
			while((line = in.readLine()) != null)
			{
				System.out.println(line);
				if(line.contains("PING"))
				{
					line = line.replace("PING", "PONG");
					send(out, line);
				}
				else if(line.contains("MODE"))
				{
					send(out, "JOIN "+channel);
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
