package org.krish858.pixelpay;

import org.bukkit.plugin.java.JavaPlugin;

public final class Pixelpay extends JavaPlugin {

    @Override
    public void onEnable() {
        PixelpayCommand command = new PixelpayCommand(this);
        getCommand("pixelpay").setExecutor(command);
        getServer().getPluginManager().registerEvents(command, this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
