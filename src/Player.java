public class Player {
    int health;
    int powerCooldown = 0;
    public Player(int health){
        this.health = health;
    }

    void takeDamage(int damage){
        health -= damage;
    }

    void heal(int amount){
        health += amount;
    }
    boolean canUsePower(){
        return powerCooldown == 0;
    }
    void usePower(){
        powerCooldown = 3;
    }

    void tickCooldown() {
        if (powerCooldown > 0){
            powerCooldown --;
        }
    }
    boolean isAlive(){
        return health > 0;
    }

}