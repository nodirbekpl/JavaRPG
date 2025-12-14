import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Player player = new Player(100);
        Enemy enemy = new Enemy(50);

        System.out.println("=== TEXT RPG ===");
        System.out.println("Command: help");

        boolean running = true;
        int turn = 1;

        while (running) {
            System.out.print("> ");
            String command = scanner.nextLine().toLowerCase();

            boolean playerActed = false;

            switch (command) {
                //InfoLine
                case "help"->{
                    System.out.println("Commands:");
                    System.out.println("- attack    : basic attacks");
                    System.out.println("- power     : strong attack (cooldown)");
                    System.out.println("- stun      : chance to stun enemy");
                    System.out.println("- heal      : restore some health");
                    System.out.println("- status    : show status");
                    System.out.println("- quit      : exit game");
                }
                // Offencive Actions
                case "attack" -> {
                    int dmg = random.nextInt(10) + 1;
                    enemy.takeDamage(dmg);
                    System.out.println("You hit the enemy for " + dmg);
                    playerActed = true;
                }
                case "power" ->{
                    if (player.canUsePower()){
                        int dmg = random.nextInt(15) + 10;
                        enemy.takeDamage(dmg);
                        player.usePower();
                        System.out.println("POWER ATTACK for " + dmg);
                        playerActed = true;
                    }
                    else {
                        System.out.println("Power attack is on cooldown!");
                    }
                }

                // Defencive / Utility
                case "heal" -> {
                    int heal = random.nextInt(5) + 1;
                    player.heal(heal);
                    System.out.println("You heal for " + heal);
                    playerActed = true;
                }
                // Information
                case "status" -> {
                    System.out.println("Player HP: " + player.health);
                    System.out.println("Enemy HP: " + enemy.health);
                    System.out.println("Power cooldown: " + player.powerCooldown);
                }
                //Exit
                case "quit" -> running = false;

                default -> System.out.println("Unknown command");
            }
            if (playerActed){
                turn++;
                if (turn % 3 == 0){
                    System.out.println("---- Turn " + turn + " ----");
                }
            }

            if (playerActed && enemy.isAlive()) {
                int roll = random.nextInt(100);
                if (roll < 70) {
                    int enemyDmg = random.nextInt(8) + 1 + (turn / 5);
                    player.takeDamage(enemyDmg);
                    System.out.println("Enemy hits you for " + enemyDmg);
                    }



                else {
                    System.out.println("Enemy hesitates...");
                }
                if (turn % 5 == 0) {
                    System.out.println("The enemy grows more aggressive!");
                }

            }

            if (!enemy.isAlive()) {
                System.out.println("You defeated the enemy!");
                running = false;
            }

            if (!player.isAlive()) {
                System.out.println("You died.");
                running = false;
            }
        }

       scanner.close();
    }
}
