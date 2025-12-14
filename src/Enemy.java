public class Enemy {
    private String name;
    private int health;
    private boolean stunned;

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
}
