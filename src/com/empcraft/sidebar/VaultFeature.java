package com.empcraft.sidebar;

import java.text.DecimalFormat;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultFeature {
	SideBar SB;
	private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;
	public VaultFeature(SideBar plugin,Plugin vault) {
		SB = plugin;
		if (!setupEconomy() ) {
			SB.msg(null,"&c[Warning] SideBar did not detect economy. Some placeholders may not work.");
			return;
        }
        setupPermissions();
        setupChat();
    	SB.addPlaceholder(new Placeholder("matchgroup",vault) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		return matchgroup(modifiers[0]);
		}
    	@Override 
		public String getDescription() {
			return "{matchgroup:STRING} - Returns the closest matching group (useful to getting the case right)";
		} });
    	SB.addPlaceholder(new Placeholder("hasperm",vault) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		if (player==null) {
    			return "true";
    		}
    		else if (modifiers.length==2) {
    			return ""+perms.playerHas(player.getWorld(),modifiers[0], modifiers[1]);
    		}
    		else if (SB.checkperm(player,modifiers[0])) {
    			return "true";
    		}
    		return "false";
		}
    	@Override 
		public String getDescription() {
			return "{hasperm:NODE} - Returns true if a player has the permission";
		} });
    	SB.addPlaceholder(new Placeholder("prefix",vault) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		if (modifiers.length==1) {
    			if (Bukkit.getPlayer(modifiers[0])==null) {
    				try {
    	    			ImprovedOfflinePlayer offlineplayer = new ImprovedOfflinePlayer(modifiers[0]);
    	    			return chat.getPlayerPrefix(offlineplayer.getLocation().getWorld(), modifiers[0]);
    	        		}
    	        		catch (Exception e) {
    	        			IOP_1_7_9 offlineplayer = new IOP_1_7_9(modifiers[0]);
    	        			return chat.getPlayerPrefix(offlineplayer.getLocation().getWorld(), modifiers[0]);
    	        		}
    			}
    			return chat.getPlayerPrefix(Bukkit.getPlayer(modifiers[0]));
    		}
    		return chat.getPlayerPrefix(player);
		}
		@Override 
		public String getDescription() {
			return "{prefix:*username} - Returns a player's prefix";
		} });
    	SB.addPlaceholder(new Placeholder("gprefix",vault) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		if (modifiers.length==1) {
    			if (Bukkit.getPlayer(modifiers[0])==null) {
    				chat.getGroupPrefix(player.getWorld(),modifiers[0]);
    			}
    			return chat.getGroupPrefix(player.getWorld(),perms.getPrimaryGroup(Bukkit.getPlayer(modifiers[0])));
    		}
    		return chat.getGroupPrefix(player.getWorld(),perms.getPrimaryGroup(player));
		}
		@Override 
		public String getDescription() {
			return "{gprefix:*username/*group} - Returns a group's prefix";
		} });
    	SB.addPlaceholder(new Placeholder("gsuffix",vault) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		if (modifiers.length==1) {
    			if (Bukkit.getPlayer(modifiers[0])==null) {
    				chat.getGroupPrefix(player.getWorld(),modifiers[0]);
    			}
    			return chat.getGroupSuffix(player.getWorld(),perms.getPrimaryGroup(Bukkit.getPlayer(modifiers[0])));
    		}
    		return chat.getGroupSuffix(player.getWorld(),perms.getPrimaryGroup(player));
		}
		@Override 
		public String getDescription() {
			return "{GSUFFIX:*username/*group} - Returns a group's suffix";
		} });
    	SB.addPlaceholder(new Placeholder("money",vault) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		if (modifiers.length==1) {
    			return new DecimalFormat("######################.##").format(econ.getBalance(modifiers[0]));
    		}
    		return new DecimalFormat("######################.##").format(econ.getBalance(player.getName()));
		}
    	@Override 
		public String getDescription() {
			return "{money:*username} - Returns a player's money";
		} });
    	SB.addPlaceholder(new Placeholder("group",vault) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		if (modifiers.length==1) {
    			if (Bukkit.getPlayer(modifiers[0])==null) {
    	    		try {
    	    			ImprovedOfflinePlayer offlineplayer = new ImprovedOfflinePlayer(modifiers[0]);
    	    			return ""+perms.getPrimaryGroup(offlineplayer.getLocation().getWorld(), modifiers[0]);
    	    		}
    	        		catch (Exception e) {
    	        			IOP_1_7_9 offlineplayer = new IOP_1_7_9(modifiers[0]);
    	        			return ""+perms.getPrimaryGroup(offlineplayer.getLocation().getWorld(), modifiers[0]);
    	        		}
    			}
    			return ""+perms.getPrimaryGroup(Bukkit.getPlayer(modifiers[0]));
    		}
    		return ""+perms.getPrimaryGroup(player);
		}
    	@Override 
		public String getDescription() {
			return "{group:*username} - Returns a player's group";
		} });
    	SB.addPlaceholder(new Placeholder("suffix",vault) { @Override public String getValue(Player player, Location location,String[] modifiers, Boolean elevation) {
    		if (modifiers.length==1) {
    			if (Bukkit.getPlayer(modifiers[0])==null) {
    				try {
    	    			ImprovedOfflinePlayer offlineplayer = new ImprovedOfflinePlayer(modifiers[0]);
    	    			return chat.getPlayerSuffix(offlineplayer.getLocation().getWorld(), modifiers[0]);
    	        		}
    	        		catch (Exception e) {
    	        			IOP_1_7_9 offlineplayer = new IOP_1_7_9(modifiers[0]);
    	        			return chat.getPlayerSuffix(offlineplayer.getLocation().getWorld(), modifiers[0]);
    	        		}
    			}
    			return chat.getPlayerSuffix(Bukkit.getPlayer(modifiers[0]));
    		}
    		return chat.getPlayerSuffix(player);
		}
    	@Override 
		public String getDescription() {
			return "{suffix:*username} - Returns a player's suffix";
		} });
	}
	private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = SB.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }
	private boolean setupEconomy() {
        if (SB.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = SB.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = SB.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    private String matchgroup(String group) {
		String[] groups = (perms.getGroups());
		for (String current:groups) {
			if (group.equalsIgnoreCase(current)) {
				return current;
			}
		}
		return "";
    }
}
