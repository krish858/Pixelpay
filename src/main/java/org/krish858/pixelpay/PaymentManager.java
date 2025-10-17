package org.krish858.pixelpay;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentManager {
    private final Pixelpay plugin;
    private final Map<UUID, PaymentRequest> requests = new ConcurrentHashMap<>();

    public PaymentManager(Pixelpay plugin) {
        this.plugin = plugin;
    }

    public PaymentRequest createPaymentRequest(Player player, String itemId, double amount) {
        UUID id = UUID.randomUUID();
        String url = "https://example.com/pay/" + id;
        PaymentRequest request = new PaymentRequest(id, player.getUniqueId(), itemId, amount, url);
        requests.put(id, request);


        player.sendMessage("§a[Pixelpay] Payment request created: §r" + url);
        player.sendMessage("§7You will receive the item after payment is confirmed (simulated)");


        Bukkit.getScheduler().runTaskLater((Plugin) plugin, () -> {

            request.setStatus(PaymentRequest.Status.COMPLETE);

            deliver(request);
        }, 200L);

        return request;
    }

    private void deliver(PaymentRequest request) {
        UUID playerId = request.getPlayerUUID();
        Player player = Bukkit.getPlayer(playerId);
        if (player == null) {
            plugin.getLogger().info("Player " + playerId + " offline; cannot deliver item for request " + request.getId());
            return;
        }


        ItemStack itemToGive;
        switch (request.getItemId()) {
            case "crypto_sword":
                itemToGive = new ItemStack(Material.DIAMOND_SWORD);
                break;
            case "rent_skin_x402":
                itemToGive = new ItemStack(Material.LEATHER_HELMET);
                break;
            default:
                itemToGive = new ItemStack(Material.PAPER);
                break;
        }

        player.getInventory().addItem(itemToGive);
        player.sendMessage("§a[Pixelpay] Payment confirmed. Item delivered: " + request.getItemId());
        plugin.getLogger().info("Delivered item for payment " + request.getId() + " to " + player.getName());
    }

    public PaymentRequest getRequest(UUID id) {
        return requests.get(id);
    }
}

