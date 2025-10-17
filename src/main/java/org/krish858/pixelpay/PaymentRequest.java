package org.krish858.pixelpay;

import java.time.Instant;
import java.util.UUID;

public class PaymentRequest {
    public enum Status { PENDING, COMPLETE, FAILED }

    private final UUID id;
    private final UUID playerUUID;
    private final String itemId;
    private final double amount;
    private final String url;
    private volatile Status status;
    private final Instant createdAt;

    public PaymentRequest(UUID id, UUID playerUUID, String itemId, double amount, String url) {
        this.id = id;
        this.playerUUID = playerUUID;
        this.itemId = itemId;
        this.amount = amount;
        this.url = url;
        this.status = Status.PENDING;
        this.createdAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getPlayerUUID() { return playerUUID; }
    public String getItemId() { return itemId; }
    public double getAmount() { return amount; }
    public String getUrl() { return url; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "id=" + id +
                ", player=" + playerUUID +
                ", itemId='" + itemId + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}

