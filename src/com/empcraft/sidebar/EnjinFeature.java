package com.empcraft.sidebar;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.enjin.officialplugin.EnjinMinecraftPlugin;
import com.enjin.officialplugin.api.EnjinAPI;
import com.enjin.officialplugin.listeners.EnjinStatsListener;
import com.enjin.officialplugin.points.EnjinPointsSyncClass;
import com.enjin.officialplugin.points.ErrorConnectingToEnjinException;
import com.enjin.officialplugin.points.PlayerDoesNotExistException;
import com.enjin.officialplugin.threaded.EnjinRetrievePlayerTags;
import com.enjin.officialplugin.stats.StatValue;

public class EnjinFeature {
	SideBar SB;
	private static final Logger log = Logger.getLogger("Minecraft");
	public EnjinFeature(SideBar plugin,Plugin enjin) {
		SB = plugin;
        SB.addPlaceholder(new Placeholder("enjintags",enjin) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
        	try {
				if (modifiers.length>0) {
					return StringUtils.join(EnjinAPI.getPlayerTags(modifiers[0]).keySet(),","); 
				}
        		return StringUtils.join(EnjinAPI.getPlayerTags(player.getName()).keySet(),",");
			} catch (PlayerDoesNotExistException e) {
				return "Err404";
			} catch (ErrorConnectingToEnjinException e) {
				return "Err408";
			}
		}
        @Override 
		public String getDescription() {
			return "{enjintags:*user} - Returns a list of tags for a user\nEnjin features are currently in beta and may be buggy";
		} });
        SB.addPlaceholder(new Placeholder("enjintag",enjin) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		try {
				return EnjinAPI.getPlayerTags(player.getName()).get(modifiers[0]).toString();
			} catch (PlayerDoesNotExistException e) {
				return "Err404";
			} catch (ErrorConnectingToEnjinException e) {
				return "Err408";
			}
		}
    	@Override 
		public String getDescription() {
			return "{enjintag:tag} - Returns the user's value for a tag)\nEnjin features are currently in beta and may be buggy";
		} });
        SB.addPlaceholder(new Placeholder("enjintag",enjin) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		try {
				return EnjinAPI.getPlayerTags(player.getName()).get(modifiers[0]).toString();
			} catch (PlayerDoesNotExistException e) {
				return "Err404";
			} catch (ErrorConnectingToEnjinException e) {
				return "Err408";
			}
		}
    	@Override 
		public String getDescription() {
			return "{enjintag:tag} - Returns the user's value for a tag)\nEnjin features are currently in beta and may be buggy";
		} });
        
	}
}