public class Enemy {
    String name;
    int health;
    boolean stunned = false;

    public Enemy(String name, int health) {
        this.name = name;
        this.health = health;
    }

    void takeDamage(int damage) {
        health -= damage;
    }

    void stun() {
        stunned = true;
    }

    boolean isStunned() {
        return stunned;
    }

    void clearStun() {
        stunned = false;
    }

    boolean isAlive() {
        return health > 0;
    }
}
