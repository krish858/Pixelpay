package org.krish858.pixelpay;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.PluginCommand;

public final class Pixelpay extends JavaPlugin {

    private PaymentManager paymentManager;

    @Override
    public void onEnable() {
        this.paymentManager = new PaymentManager(this);

        PixelpayCommand command = new PixelpayCommand(this);
        PluginCommand pluginCommand = getCommand("pixelpay");
        if (pluginCommand != null) {
            pluginCommand.setExecutor(command);
        } else {
            getLogger().warning("Command 'pixelpay' not found in plugin.yml. The command won't be registered.");
        }
        getServer().getPluginManager().registerEvents(command, this);
    }

    public PaymentManager getPaymentManager() {
        return paymentManager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
