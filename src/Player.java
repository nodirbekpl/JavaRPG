public class Player {
    int health;
    public Player(int health){
        this.health = health;
    }

    void takeDamage(int damage){
        health -= damage;
    }

    void heal(int amount){
        health += amount;
    }
    boolean isAlive(){
        return health > 0;
    }

}