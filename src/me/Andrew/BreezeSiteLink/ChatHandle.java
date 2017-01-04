package me.Andrew.BreezeSiteLink;

import org.bukkit.ChatColor;

public class ChatHandle {
Main main;
public ChatHandle(){
	main = Main.getInstance();
}
public String getMessage(message MsgType){
	String prefix = main.getConfig().getString("Messages.prefix");
	return cc(prefix+fromConfig(MsgType));
}
public String fromConfig(message MsgType){
	if(MsgType == message.appPosted){
		return("&aYour staff application has been posted to: {LINK}");
	}
	return main.getConfig().getString("Messages."+MsgType.toString());
}
public String cc(String str){
	return ChatColor.translateAlternateColorCodes('&', str);
}
}
