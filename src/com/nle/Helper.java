package com.nle;

public class Helper 
{
	public static String correctNull(final String str)
	{
		if(str==null || str.equalsIgnoreCase(""))
		{
			return "";
		}
		else
		{
			return str;
		}
	}
}
