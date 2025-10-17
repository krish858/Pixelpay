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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class PixelpayCommand implements CommandExecutor, Listener {

    private final Pixelpay plugin;

    // Centralized component for the shop title so we can compare it later
    private static final Component SHOP_TITLE = LegacyComponentSerializer.legacySection().deserialize("§6Pixelpay Shop");

    public PixelpayCommand(Pixelpay plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        Inventory gui = Bukkit.createInventory(null, 27, SHOP_TITLE);

        // Fill background with gray stained glass panes to give a UI-like look
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);
        for (int i = 0; i < 27; i++) {
            gui.setItem(i, filler);
        }

        // Create a cross/close button (Barrier) at the top right (slot 8)
        ItemStack closeButton = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = closeButton.getItemMeta();
        closeMeta.setDisplayName("§cClose");
        List<String> closeLore = new ArrayList<>();
        closeLore.add("§7Exit the Pixelpay UI");
        closeMeta.setLore(closeLore);
        closeButton.setItemMeta(closeMeta);
        gui.setItem(8, closeButton);

        // Example marketplace item: Crypto Sword
        ItemStack cryptoSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordMeta = cryptoSword.getItemMeta();
        swordMeta.setDisplayName("§bCrypto Sword");
        List<String> swordLore = new ArrayList<>();
        swordLore.add("§7Price: §6100 USD (placeholder)");
        swordLore.add("§7Click to purchase with crypto");
        swordMeta.setLore(swordLore);
        cryptoSword.setItemMeta(swordMeta);
        gui.setItem(11, cryptoSword);

        // Example rent item: Rent Skin (x402)
        ItemStack rentSkin = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta rentMeta = rentSkin.getItemMeta();
        rentMeta.setDisplayName("§aRent Skin (x402)");
        List<String> rentLore = new ArrayList<>();
        rentLore.add("§7Pay only when you use it");
        rentLore.add("§7Click to rent (placeholder)");
        rentMeta.setLore(rentLore);
        rentSkin.setItemMeta(rentMeta);
        gui.setItem(13, rentSkin);

        player.openInventory(gui);
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (SHOP_TITLE.equals(event.getView().title())) {

            int raw = event.getRawSlot();
            if (raw == 8) { // close button
                event.getWhoClicked().closeInventory();
                return;
            }

            // Only allow clicks within the inventory area
            if (raw < 27) {
                ItemStack clicked = event.getCurrentItem();
                if (clicked == null || !clicked.hasItemMeta()) return;
                ItemMeta meta = clicked.getItemMeta();
                if (meta == null || meta.getDisplayName() == null) return;
                String name = meta.getDisplayName();

                Player player = (Player) event.getWhoClicked();
                if (name.equals("§bCrypto Sword")) {
                    if (plugin.getPaymentManager() == null) {
                        player.sendMessage("§c[Pixelpay] Payment subsystem unavailable.");
                        player.closeInventory();
                        return;
                    }
                    double price = 100.0;
                    PaymentRequest req = plugin.getPaymentManager().createPaymentRequest(player, "crypto_sword", price);
                    player.sendMessage("§a[Pixelpay] Payment URL: §r" + req.getUrl());
                    player.sendMessage("§7Simulated payment will confirm in ~10s and deliver the item.");
                    player.closeInventory();
                } else if (name.equals("§aRent Skin (x402)")) {
                    player.closeInventory();
                    player.sendMessage("§e[Pixelpay] Rent flow not implemented. Placeholder for renting via x402.");
                }
            }
        }
    }
}
