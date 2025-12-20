public class Enemy {
    private String name;
    private int health;
    private boolean stunned = false;

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
        this.stunned = false;
    }

    public String getName() {
        return name;
    }
    public int getHealth(){
        return health;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void stun() {
        stunned = true;
    }

    public void clearStun() {
        stunned = false;
    }
    private boolean stunImmune = false;

    public boolean isStunImmune() {
        return stunImmune;
    }

    public void giveStunImmunity() {
        stunImmune = true;
    }

    public void clearStunImmunity() {
        stunImmune = false;
    }


    public String getLoot() {
        return switch (name.toLowerCase()){
            case "goblin" -> "Small Potion";
            case "orc" -> "Strength Potion";
            case "skeleton" -> "Shield";
            default -> null;
        };
    }
}
