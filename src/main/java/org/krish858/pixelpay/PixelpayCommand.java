package org.krish858.pixelpay;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PixelpayCommand implements CommandExecutor, Listener {

    private final Pixelpay plugin;

    public PixelpayCommand(Pixelpay plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        Inventory gui = Bukkit.createInventory(null, 27, "Pixelpay Shop");

        // Create a cross button (Barrier) at the top right (slot 8)
        ItemStack closeButton = new ItemStack(Material.BARRIER);
        ItemMeta meta = closeButton.getItemMeta();
        meta.setDisplayName("§cClose");
        closeButton.setItemMeta(meta);
        gui.setItem(8, closeButton);

        player.openInventory(gui);
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Pixelpay Shop")) {
            if (event.getRawSlot() == 8) {
                event.getWhoClicked().closeInventory();
                event.setCancelled(true);
            } else if (event.getRawSlot() < 27) {
                event.setCancelled(true);
            }
        }
    }
}
