package com.nle;

import java.util.List;

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
	public static int indexOccurence(String str, String find, int n)
	{
		int pos = -1;
		do
		{
			pos = str.indexOf(find, pos+1);
		}while(n-- > 0 && pos != -1);
		
		return pos;
	}
	public static boolean contains(String str, List<String> list)
	{
		boolean res = false;
		for(String s : list)
		{
			if(str.contains(s))
				res = true;
		}
		return res;
	}
}
