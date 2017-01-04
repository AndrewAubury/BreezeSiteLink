package me.Andrew.BreezeSiteLink;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.Andrew.XenforoAPI.SiteAPI;

public class Main extends JavaPlugin {

	private static Main ourinstance = null;

	public static Main getInstance() {
		return ourinstance;
	}

	String API_KEY;
	String API_LINK;
	SiteAPI api;
	@Override
	public void onEnable() {
		ourinstance = this;
		api = new SiteAPI(API_LINK, API_KEY);
		saveDefaultConfig();
		if ((!getConfig().contains("API.KEY")) || (!getConfig().contains("API.LINK"))) {
			getLogger().severe("Plugin Not Configured!");
			getServer().getPluginManager().disablePlugin(this);
		}
		API_KEY = getConfig().getString("API.KEY");
		API_LINK = getConfig().getString("API.LINK");
		if ((API_KEY.trim() == "") || (API_LINK.trim() == "")) {
			getLogger().severe("Plugin Not Configured!");
			getServer().getPluginManager().disablePlugin(this);
		}
		if (!(api.canConnect())) {
			getLogger().severe("API Can Not Connect!");
			getServer().getPluginManager().disablePlugin(this);
		}
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("register")) {
			// /register (email) (password)
			if (!(sender instanceof Player)) {
				return true;
			}
			ChatHandle ch = new ChatHandle();
			Player p = (Player) sender;
			
			if(api.userExists(p.getName())){
				p.sendMessage(ch.getMessage(message.userAlreadyMade));
				return true;
			}
				if (args.length == 2) {
					String email = args[0];
					String pass = args[1];
					if(pass.length() < 7){
						p.sendMessage(ch.getMessage(message.passwordTooSmall));
						return true;
					}
					if(!isEmailAddress(email)){
						p.sendMessage(ch.getMessage(message.emailNotValid));
						return true;
					}
				
					if(api.registerUser(p.getName(), email, pass)){
						p.sendMessage(ch.getMessage(message.userMade).replace("{PASS}", pass));
					}else{
						p.sendMessage(ch.getMessage(message.error));
					}
				}else{
					p.sendMessage(ch.getMessage(message.usage));
				} 
		}else if(label.equalsIgnoreCase("testPoster")){
			if (!(sender instanceof Player)) {
				return true;
			}
			ChatHandle ch = new ChatHandle();
			Player p = (Player) sender;
			
			String link = new APICaller().postApplication(p, "This is just to test perms of the plugin");
			p.sendMessage(ch.getMessage(message.appPosted).replace("{LINK}", link));
		}
		return true;
	}

	public static boolean isEmailAddress(String email) {
		return email.contains(".") && email.contains("@");
	}
}
