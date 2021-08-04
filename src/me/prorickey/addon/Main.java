package me.prorickey.addon;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

 
public class Main extends JavaPlugin {
 
   Main instance;
   SkriptAddon addon;
 
   public void onEnable() {
       instance = this;
       addon = Skript.registerAddon(this);
       try {
    	   addon.loadClasses("me.prorickey.addon", "elements");
       } catch (IOException e) {
           e.printStackTrace();
       }
       Bukkit.getLogger().info(ChatColor.GREEN + "[ProrickeySK] has been enabled!");
   }
 
   public Main getInstance() {
       return instance;
   }
 
   public SkriptAddon getAddonInstance() {
       return addon;
   }
   
}