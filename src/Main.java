import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        Player player = new Player(100);

        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy("Goblin", 30));
        enemies.add(new Enemy("Orc", 50));
        enemies.add(new Enemy("Skeleton", 40));

        Enemy currentEnemy = enemies.get(0);

        boolean running = true;
        int turn = 1;

        System.out.println("=== TEXT RPG CLEAN ENGINE ===");
        System.out.println("Command: help for more info");
        Enemy lastAnnouncedEnemy = null;

        while (running) {

            if (currentEnemy != null && currentEnemy != lastAnnouncedEnemy){
                System.out.println("Fighting: " + currentEnemy.getName());
                lastAnnouncedEnemy = currentEnemy;
            }



            // 1️⃣ TURN HEADER
            System.out.println("\n---- Turn " + turn + " ----");

            // 2️⃣ PLAYER INPUT
            System.out.print("> ");
            String input = scanner.nextLine().toLowerCase();

            boolean playerActed = false;

            // 3️⃣ PLAYER ACTION RESOLUTION
            switch (input) {
                case "help" -> {
                    System.out.println("Commands:");
                    System.out.println("attack [enemy#] - attack enemy");
                    System.out.println("power           - strong attack");
                    System.out.println("stun            - chance to stun");
                    System.out.println("heal            - heal yourself");
                    System.out.println("status          - show info");
                    System.out.println("inventory       - show items");
                    System.out.println("use [number]    - use item");
                    System.out.println("quit            - exit");
                }


                case "attack" -> {
                    int dmg = random.nextInt(10) + 1;
                    currentEnemy.takeDamage(dmg);
                    System.out.println("You hit " + currentEnemy.getName() + " for " + dmg);
                    playerActed = true;
                }

                case "power" -> {
                    if (!player.canUsePower()) {
                        System.out.println("Power is on cooldown! (" + player.getPowerCooldown() + " turns left)");
                        break;
                    }

                    int dmg = random.nextInt(12) + 12;   // strong damage range 12–23
                    currentEnemy.takeDamage(dmg);
                    System.out.println("You unleash a POWER ATTACK on " +
                            currentEnemy.getName() + " for " + dmg + " damage!");

                    player.usePower();
                    playerActed = true;
                }
                case "stun" -> {

                    if (currentEnemy == null || !currentEnemy.isAlive()) {
                        System.out.println("There is nothing to stun.");
                        break;
                    }
                    if (currentEnemy.isStunImmune()) {
                        System.out.println(currentEnemy.getName() + " resists stun!");
                        playerActed = true;
                        break;
                    }

                    if (currentEnemy.isStunned()) {
                        System.out.println(currentEnemy.getName() + " is already stunned!");
                        break;
                    }

                    int roll = random.nextInt(100);  // 0–99
                    if (roll < 40) {                  // 40% success
                        currentEnemy.stun();
                        System.out.println("You stunned " + currentEnemy.getName() + "!");
                    } else {
                        System.out.println("Stun failed.");
                    }

                    playerActed = true;
                }



                case "heal" -> {
                    int heal = random.nextInt(6) + 5;
                    player.heal(heal);
                    System.out.println("You healed " + heal);
                    playerActed = true;
                }

                case "status" -> {
                    System.out.println("==== Status ====");
                    System.out.println("Player HP: " + player.getHealth());
                   if (player.getPowerCooldown() != 0){
                       System.out.println("Power cooldown: " + player.getPowerCooldown());
                   } else {
                       System.out.println("Power cooldown: Ready");
                   }
                    System.out.println("Enemy stunned: " + (currentEnemy != null && currentEnemy.isStunned()));

                    int i = 1;
                    System.out.println("Enemies:");
                    for (Enemy e : enemies) {

                        String marker = (e == currentEnemy && e.isAlive()) ? " <== fighting" : "";

                        String statusText;
                        if (!e.isAlive()) {
                            statusText = "DEAD";
                        } else {
                            statusText = "HP: " + e.getHealth();
                        }

                        System.out.println(i + ". " + e.getName() + " (" + statusText + ") " + marker);
                        i++;
                    }

                    System.out.println("==== Status ====");
                }

                case "quit" -> {
                    System.out.println("You quit the game.");
                    running = false;
                }

                default -> System.out.println("Unknown command.");
            }

            // if action does NOT consume turn → skip enemy
            if (!playerActed) continue;

            if(playerActed) {
                player.tickCooldown();
            }


            // 4️⃣ IF ENEMY DIES → END TURN IMMEDIATELY
            if (!currentEnemy.isAlive()) {
                System.out.println(currentEnemy.getName() + " is defeated!");

                // move to next enemy
                int next = enemies.indexOf(currentEnemy) + 1;

                if (next < enemies.size()) {
                    currentEnemy = enemies.get(next);
                    System.out.println("A new enemy approaches: " + currentEnemy.getName());
                    System.out.println("Press ENTER to continue...");
                    scanner.nextLine();
                } else {
                    System.out.println("You defeated all enemies!");
                    running = false;
                }

                continue; // VERY IMPORTANT
            }

            // 5️⃣ ENEMY ACTION
            if (playerActed && currentEnemy != null && currentEnemy.isAlive()) {

                if (currentEnemy.isStunned()) {
                    System.out.println(currentEnemy.getName() + " is stunned and cannot attack!");
                    currentEnemy.clearStun();
                    currentEnemy.giveStunImmunity();
                }

                else {
                    int roll = random.nextInt(100);

                    if (roll < 70) {
                        int dmg = random.nextInt(8) + 1;
                        player.takeDamage(dmg);
                        System.out.println(currentEnemy.getName() + " hits you for " + dmg);
                    } else {
                        System.out.println(currentEnemy.getName() + " hesitates...");
                    }

                    // Enemy completed a normal turn → immunity expires
                    currentEnemy.clearStunImmunity();
                }
            }



            // 6️⃣ PLAYER DEATH
            if (!player.isAlive()) {
                System.out.println("You died.");
                running = false;
            }

            // 7️⃣ TURN END
            turn++;
        }

        scanner.close();
    }
}
