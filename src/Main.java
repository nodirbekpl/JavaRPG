import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Player player = new Player(100);
        Enemy enemy = new Enemy(50);

        System.out.println("=== TEXT RPG ===");
        System.out.println("Commands: attack, heal, status, quit");

        boolean running = true;

        while (running) {
            System.out.print("> ");
            String command = scanner.nextLine().toLowerCase();

            boolean playerActed = false;

            switch (command) {
                case "attack" -> {
                    int dmg = random.nextInt(10) + 1;
                    enemy.takeDamage(dmg);
                    System.out.println("You hit the enemy for " + dmg);
                    playerActed = true;
                }

                case "heal" -> {
                    int heal = random.nextInt(5) + 1;
                    player.heal(heal);
                    System.out.println("You heal for " + heal);
                    playerActed = true;
                }

                case "status" -> {
                    System.out.println("Player HP: " + player.health);
                    System.out.println("Enemy HP: " + enemy.health);
                }

                case "quit" -> running = false;

                default -> System.out.println("Unknown command");
            }

            if (playerActed && enemy.isAlive()) {
                int enemyDmg = random.nextInt(8) + 1;
                player.takeDamage(enemyDmg);
                System.out.println("Enemy hits you for " + enemyDmg);
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
