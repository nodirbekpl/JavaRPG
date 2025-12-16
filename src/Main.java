import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Enemy currentEnemy = null;


        Player player = new Player(100);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Goblin", 30));
        enemies.add(new Enemy("Orc", 50));
        enemies.add(new Enemy("Skeleton", 40));

        System.out.println("=== TEXT RPG ===");
        System.out.println("Command: help");

        boolean running = true;
        int turn = 1;

        while (running) {
            System.out.print("> ");
            String command = scanner.nextLine().toLowerCase();
            String[] parts = command.split(" ");
            String action = parts[0];

            boolean playerActed = false;
            for (Enemy e : enemies) {
                if (e.isAlive()) {
                    currentEnemy = e;
                    break;
                }
            }


            switch (action) {
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
                    if (parts.length == 2){
                        int targetIndex;

                        try {
                            targetIndex = Integer.parseInt(parts[1]);
                        }catch (NumberFormatException e) {
                            System.out.println("Invalid target number. ");
                            break;
                        }
                        List<Enemy> aliveEnemies = new ArrayList<>();
                        for (Enemy e : enemies){
                            if (e.isAlive()){
                                aliveEnemies.add(e);
                            }
                        }
                        if (targetIndex < 1 || targetIndex > aliveEnemies.size()) {
                            System.out.println("No such enemy. ");
                            break;
                        }
                        Enemy selected = aliveEnemies.get(targetIndex - 1);

                        if ( selected != currentEnemy){
                            currentEnemy = selected;
                            System.out.println("You switch target to " + currentEnemy.getName());
                        }
                        int dmg = random.nextInt(10) + 1;
                        currentEnemy.takeDamage(dmg);
                        System.out.println("You attack " + currentEnemy.getName() + " for " + dmg);
                        playerActed = true;
                    } else {
                        // fallback: attack current enemy
                        int dmg = random.nextInt(10) + 1;
                        currentEnemy.takeDamage(dmg);
                        System.out.println("You attack " + currentEnemy.getName() + " for " + dmg);
                        playerActed = true;
                    }

                }
                case "power" -> {
                    if (player.canUsePower()){
                        int dmg = random.nextInt(15) + 10;
                        currentEnemy.takeDamage(dmg);

                        player.usePower();
                        System.out.println("POWER ATTACK for " + dmg);
                        playerActed = true;
                    }
                    else {
                        System.out.println("Power attack is on cooldown!");
                    }
                }
                case "stun" ->{
                    int roll = random.nextInt(100);
                    if(!currentEnemy.isStunned() && roll < 40){
                        currentEnemy.stun();
                        System.out.println("Enemy is stunned!");
                    }
                    else {
                        System.out.println("Stun failed.");
                    }
                    playerActed = true;
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
                    System.out.println("==== Status ====");
                    System.out.println("Player HP: " + player.health);
                    System.out.println("Power cooldown: " + player.powerCooldown);
                    System.out.println("Enemy stunned: " + currentEnemy.isStunned());
                    int index = 1;
                    System.out.println("Enemies:");
                    for (Enemy e : enemies) {
                        if (e.isAlive()){
                            String marker = (e == currentEnemy) ? " <== FIGHTING" : "";
                            System.out.println(index + ". " + e.getName() + " ( HP: " + e.getHealth() + ")" + marker);
                            index ++;
                        }
                    }
                    System.out.println("============");
                }
                //Exit
                case "quit" -> running = false;

                default -> System.out.println("Unknown command");
            }
            if (playerActed){
                turn++;
                player.tickCooldown();
                if (turn % 3 == 0){
                    System.out.println("---- Turn " + turn + " ----");
                }
            }

            if (playerActed && currentEnemy != null && currentEnemy.isAlive()) {
                if (currentEnemy.isStunned()){
                    currentEnemy.clearStun();
                } else {
                    int roll = random.nextInt(100);
                    if (roll < 70) {
                        int enemyDmg = random.nextInt(8) + 1 + (turn / 5);
                        player.takeDamage(enemyDmg);
                        System.out.println(currentEnemy.getName() + " hits you for " + enemyDmg);
                    } else {
                        System.out.println("Enemy hesitates...");
                    }
                }
                if (turn % 5 == 0) {
                    System.out.println("The enemy grows more aggressive!");
                }

            }

           boolean allDead = true;
            for (Enemy e : enemies){
                if (e.isAlive()){
                    allDead = false;
                    break;
                }
            }
            if (allDead){
                System.out.println("You defeated all enemies!");
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
