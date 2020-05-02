package com.nle;

import java.util.Arrays;
import java.util.List;

public interface ApplicationProperties 
{
	final String FILE_PATH = "D://Clients/NLE/logs/";
	final List<String> COMMANDS = Arrays.asList("PRIVMSG,NAMES".split(","));
}
