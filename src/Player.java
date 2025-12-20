import java.util.ArrayList;
import java.util.List;

public class Player {

    private int health;
    private final int maxHealth;

    private int powerCooldown = 0;

    private final List<String> inventory = new ArrayList<>();


    public Player(int health) {
        this.health = health;
        this.maxHealth = health;
    }


    // ===== HEALTH =====
    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int amount) {
        health = Math.max(0, health - amount);
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }


    // ===== POWER COOLDOWN =====
    public boolean canUsePower() {
        return powerCooldown == 0;
    }

    public void usePower() {
        powerCooldown = 3;
    }

    public void tickCooldown() {
        if (powerCooldown > 0) powerCooldown--;
    }

    public int getPowerCooldown() {
        return powerCooldown;
    }


    // ===== INVENTORY =====
    public void addItem(String item) {
        inventory.add(item);
    }

    public boolean hasItems() {
        return !inventory.isEmpty();
    }

    public List<String> getInventory() {
        return inventory;
    }

    public String useItem(int index) {
        if (index < 0 || index >= inventory.size()) return null;
        return inventory.remove(index);
    }

    public String peekItem(int index) {
        if (index < 0 || index >= inventory.size()) return null;
        return inventory.get(index);
    }
}
