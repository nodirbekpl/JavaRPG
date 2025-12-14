public class Enemy {
    int health;
    public Enemy(int health){
        this.health = health;
    }

    void takeDamage(int damage){
        health -= damage;
    }

    boolean isAlive(){
        return health > 0;
    }
}
