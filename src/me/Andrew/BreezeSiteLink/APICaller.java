package me.Andrew.BreezeSiteLink;

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

import me.Andrew.XenforoAPI.APIAction;
import me.Andrew.XenforoAPI.SiteAPI;

public class APICaller {
	
	SiteAPI api = Main.getInstance().api;
	public String postApplication(Player p, String message) {
		String name = p.getName();
		String added = api.addGroup(name, 10);
		String doneLink = "";
		message += "|" + added + "|";

		HashMap<String, String> args = new HashMap<>();
		args.put("grab_as", name);
		args.put("node_id", "8");
		args.put("title", name + "'s%20junior%20moderator%20application");
		args.put("prefix_id", "1");
		args.put("message", message);

		try {
			String postLink = api.callAPI(APIAction.createthread, args);
			JSONObject json = new JSONObject(postLink);
			int id = json.getInt("thread_id");
			api.removeGroup(p.getName(), 10);
			doneLink = "https://breezemc.com/threads/" + id + "/";
		} catch (JSONException e) {
			return "";
		}
		return doneLink;
	}	
}