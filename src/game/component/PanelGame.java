package game.component;

import game.obj.Bullet;
import game.obj.Effect;
import game.obj.Player;
import game.obj.Rocket;
import game.obj.sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * PanelGame is the main component that handles the game's logic, drawing, and user input.
 * It extends JComponent and manages the game's state, including player movement,
 * bullet and rocket management, and collision detection.
 *
 */
public class PanelGame extends JComponent {

    // Graphics and image-related fields
    private Graphics2D g2;
    private BufferedImage image;
    private int width;
    private int height;
    private Thread thread; // Main game loop thread
    private boolean start = true; // Flag to control the game loop
    private Key key; // Object to manage keyboard input
    private int shotTime; // Counter to manage shooting rate
    private int score = 0;
    private Sound sound;

    // Game Objects
    private Player player;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    private List<Effect> boomEffects;

    // Game FPS (Frames Per Second) settings
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS; // Time per frame in nanoseconds

    /**
     * Starts the game by initializing objects, setting up the game loop, and handling input.
     */
    public void start() {
        // Initialize the game dimensions and graphics
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Start the main game loop in a new thread
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    long startTime = System.nanoTime();
                    drawBackground(); // Draw the game background
                    drawGame(); // Draw the game objects
                    render(); // Render the drawn image to the screen
                    long time = System.nanoTime() - startTime;
                    if (time < TARGET_TIME) {
                        long sleep = (TARGET_TIME - time) / 1000000;
                        sleep(sleep); // Sleep to maintain consistent FPS
                    }
                }
            }
        });

        // Initialize game objects and start input handling
        initObjectGame();
        initKeyboard();
        initBullets();
        thread.start(); // Start the game loop
    }

    /**
     * Adds rockets to the game at random locations.
     */
    private void addRocket() {
        Random ran = new Random();
        int locationY = ran.nextInt(height - 50) + 25;

        // Create a new rocket from the left side
        Rocket rocket = new Rocket();
        rocket.changeLocation(0, locationY);
        rocket.changeAngel(0);
        rockets.add(rocket);

        // Create a new rocket from the right side
        int locationY2 = ran.nextInt(height - 50) + 25;
        Rocket rocket2 = new Rocket();
        rocket2.changeLocation(width, locationY2);
        rocket2.changeAngel(180);
        rockets.add(rocket2);
    }

    /**
     * Initializes the game objects (player, rockets, effects).
     */
    private void initObjectGame() {
        sound = new Sound();
        player = new Player();
        player.changeLocation(650, 350);
        rockets = new ArrayList<>();
        boomEffects = new ArrayList<>();

        // Continuously add rockets at intervals in a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    addRocket();
                    sleep(3000); // Delay between rocket spawns
                }
            }
        }).start();
    }

    /**
     * Initializes keyboard input handling.
     */
    private void initKeyboard() {
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Handle key presses for movement and shooting
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    key.setKey_left(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    key.setKey_right(true);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP ) {
                    key.setKey_space(true);
                } else if (e.getKeyCode() == KeyEvent.VK_J || e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    key.setKey_j(true);
                } else if (e.getKeyCode() == KeyEvent.VK_K || e.getKeyCode() == KeyEvent.VK_ALT) {
                    key.setKey_k(true);
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Handle key releases to stop movement or shooting
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    key.setKey_left(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    key.setKey_right(false);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP ) {
                    key.setKey_space(false);
                } else if (e.getKeyCode() == KeyEvent.VK_J || e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    key.setKey_j(false);
                } else if (e.getKeyCode() == KeyEvent.VK_K || e.getKeyCode() == KeyEvent.VK_ALT) {
                    key.setKey_k(false);
                }else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(false);
                }
            }
        });

        // Handle continuous input in a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                float s = 0.5f; // Speed of player rotation
                while (start) {
                    if (player.isAlive()){
                        float angle = player.getAngle();
                        if (key.isKey_left()) {
                            angle -= s;
                        }
                        if (key.isKey_right()) {
                            angle += s;
                        }
                        if (key.isKey_j() || key.isKey_k()) {
                            if (shotTime == 0) {
                                if (key.isKey_j()) {
                                    bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 5, 3f));
                                } else {
                                    bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 20, 3f));
                                }
                                sound.soundShoot();
                            }
                            shotTime++;
                            if (shotTime == 15) {
                                shotTime = 0;
                            }
                        } else {
                            shotTime = 0;
                        }
                        if (key.isKey_space()) {
                            player.speedUp();
                        } else {
                            player.speedDown();
                        }
                        player.update();
                        player.changeAngel(angle);
                    }else {
                        if(key.isKey_enter()){
                            resetGame();
                        }
                    }
                    for (int i = 0; i < rockets.size(); i++) {
                        Rocket rocket = rockets.get(i);
                        if (rocket != null) {
                            rocket.update();
                            if (!rocket.check(width, height)) {
                                rockets.remove(rocket); // Remove rocket if out of bounds
                            }else {
                                if (player.isAlive()){
                                    checkPlayer(rocket);
                                }
                            }
                        }
                    }
                    sleep(5); // Delay for smooth input processing
                }
            }
        }).start();
    }

    private void resetGame() {
        rockets.clear();
        bullets.clear();
        player.changeLocation(650, 350);
        player.reset();
        sound.resetGameOverFlag();
        score = 0;
    }

    /**
     * Initializes bullet handling in the game.
     */
    private void initBullets() {
        bullets = new ArrayList<>();

        // Handle bullet updates in a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet != null) {
                            bullet.update();
                            checkBullets(bullet); // Check for collisions
                            if (bullet.check(width, height)) {
                                bullets.remove(bullet); // Remove bullet if out of bounds
                            }
                        } else {
                            bullets.remove(bullet);
                        }
                    }
                    for (int i = 0; i < boomEffects.size(); i++) {
                        Effect boomEffect = boomEffects.get(i);
                        if (boomEffect != null) {
                            boomEffect.update();
                            if (!boomEffect.check()) {
                                boomEffects.remove(boomEffect); // Remove effect if it's over
                            }
                        } else {
                            boomEffects.remove(boomEffect);
                        }
                    }
                    sleep(1); // Delay for smooth bullet processing
                }
            }
        }).start();
    }

    /**
     * Checks for collisions between bullets and rockets.
     *
     * @param bullet The bullet to check for collisions.
     */
    public void checkBullets(Bullet bullet) {
        // Iterate through the list of rockets to check for collisions with the bullet
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                // Create an Area object from the bullet's shape and check for intersection with the rocket's shape
                Area area = new Area(bullet.getShape());
                area.intersect(rocket.getRocketShape());

                // If the bullet and rocket intersect, create explosion effects and remove the rocket
                if (!area.isEmpty()) {

                    if (!rocket.updateHP(bullet.getSize())) { // The condition is always true; this if statement seems redundant
                        score++;
                        rockets.remove(rocket); // Remove the rocket from the list
                        sound.soundDestroy();
                        // Add the initial explosion effect at the bullet's location
                        boomEffects.add(new Effect(bullet.getCenterX(), bullet.getCenterY(), 50, 50, 60, 0.3f, new Color(230, 207, 105)));

                        // Calculate the center of the rocket for placing explosion effects
                        double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                        double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;

                        // Add multiple explosion effects at the rocket's location with different parameters
                        boomEffects.add(new Effect(x, y, 45, 55, 15, 0.35f, new Color(228, 204, 77)));
                        boomEffects.add(new Effect(x, y, 65, 15, 11, 0.05f, new Color(236, 76, 41)));
                        boomEffects.add(new Effect(x, y, 35, 10, 11, 0.04f, new Color(83, 82, 82)));
                        boomEffects.add(new Effect(x, y, 85, 5, 11, 0.07f, new Color(255, 255, 255)));
                        boomEffects.add(new Effect(x, y, 15, 8, 60, 0.05f, new Color(246, 153, 87)));
                    }else{
                        sound.soundHit();
                    }
                    bullets.remove(bullet); // Remove the bullet after the collision
                    break; // Exit the loop after processing the collision
                }
            }
        }
    }

    /**
     * Checks for collisions between bullets and rockets.
     *
     * @param rocket The rocket to check for collisions.
     */
    public void checkPlayer(Rocket rocket) {
            if (rocket != null) {
                // Create an Area object from the Player's shape and check for intersection with the rocket's shape
                Area area = new Area(player.getShape());
                area.intersect(rocket.getRocketShape());
                // If the bullet and rocket intersect, create explosion effects and remove the rocket
                if (!area.isEmpty()) {
                    double rocketHp = rocket.getHp();
                    if (!rocket.updateHP(player.getHp())) { // The condition is always true; this if statement seems redundant
                        rockets.remove(rocket); // Remove the rocket from the list
                        sound.soundDestroy();
                        // Add the initial explosion effect at the bullet's location
                        boomEffects.add(new Effect(rocket.getX(), rocket.getY(), 50, 50, 60, 0.3f, new Color(230, 207, 105)));
                        // Calculate the center of the rocket for placing explosion effects
                        double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                        double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;

                        // Add multiple explosion effects at the rocket's location with different parameters
                        boomEffects.add(new Effect(x, y, 45, 55, 15, 0.35f, new Color(228, 204, 77)));
                        boomEffects.add(new Effect(x, y, 65, 15, 11, 0.05f, new Color(236, 76, 41)));
                        boomEffects.add(new Effect(x, y, 35, 10, 11, 0.04f, new Color(83, 82, 82)));
                        boomEffects.add(new Effect(x, y, 85, 5, 11, 0.07f, new Color(255, 255, 255)));
                        boomEffects.add(new Effect(x, y, 15, 8, 60, 0.05f, new Color(246, 153, 87)));
                    }
                    if (!player.updateHP(rocketHp)) { // The condition is always true; this if statement seems redundant
                        player.setAlive(false);
                        sound.soundDestroy();
                        // Add the initial explosion effect at the bullet's location
                        boomEffects.add(new Effect(player.getX(), player.getY(), 50, 50, 60, 0.3f, new Color(230, 207, 105)));
                        // Calculate the center of the rocket for placing explosion effects
                        double x = player.getX() + Player.PLAYER_SIZE / 2;
                        double y = player.getY() + Player.PLAYER_SIZE  / 2;

                        // Add multiple explosion effects at the rocket's location with different parameters
                        boomEffects.add(new Effect(x, y, 45, 55, 15, 0.35f, new Color(228, 204, 77)));
                        boomEffects.add(new Effect(x, y, 65, 15, 11, 0.05f, new Color(236, 76, 41)));
                        boomEffects.add(new Effect(x, y, 35, 10, 11, 0.04f, new Color(83, 82, 82)));
                        boomEffects.add(new Effect(x, y, 85, 5, 11, 0.07f, new Color(255, 255, 255)));
                        boomEffects.add(new Effect(x, y, 15, 8, 60, 0.05f, new Color(246, 153, 87)));
                    }
                }

            }

    }

    private void drawBackground() {
        // Set the background color to a dark gray and fill the entire panel
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, width, height);
    }

    private void drawGame() {
        // Draw the player character
        if (player.isAlive()){
            player.draw(g2);
        }
        // Draw all bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null) {
                bullet.draw(g2);
            }
        }

        // Draw all rockets
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                rocket.draw(g2);
            }
        }

        // Draw all explosion effects
        for (int i = 0; i < boomEffects.size(); i++) {
            Effect boomEffect = boomEffects.get(i);
            if (boomEffect != null) {
                boomEffect.draw(g2);
            }
        }
        g2.setColor(Color.WHITE);
        g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
        g2.drawString("Score: " + score, 10, 20);

        if (!player.isAlive()) {
            String text = "GAME OVER";
            String textKey = "Press Key enter to Continue";
            String scoreText = "SCORE: " + score;

            // Set font for "GAME OVER"
            g2.setFont(getFont().deriveFont(Font.BOLD, 50f));
            FontMetrics fm = g2.getFontMetrics();

            // Calculate the dimensions of "GAME OVER"
            Rectangle2D r2 = fm.getStringBounds(text, g2);
            double textWidth = r2.getWidth();
            double textHeight = r2.getHeight();
            double x = (width - textWidth) / 2;
            double y = (height - textHeight) / 2;

            // Draw "GAME OVER"
            g2.drawString(text, (int) x, (int) (y + fm.getAscent()));

            // Set font for score text
            g2.setFont(getFont().deriveFont(Font.BOLD, 25f));
            fm = g2.getFontMetrics(); // Update FontMetrics for the new font size

            // Calculate the width of the score text for proper centering
            r2 = fm.getStringBounds(scoreText, g2);
            double scoreTextWidth = r2.getWidth();

            // Calculate the position for scoreText centered beneath "GAME OVER"
            double scoreTextY = y + textHeight + 20; // Add some spacing between "GAME OVER" and score
            double scoreTextX = (width - scoreTextWidth) / 2;
            g2.drawString(scoreText, (int) scoreTextX, (int) (scoreTextY + fm.getAscent()));

            // Set font for key instructions
            g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
            fm = g2.getFontMetrics(); // Update FontMetrics for the new font size
            r2 = fm.getStringBounds(textKey, g2);
            double textKeyWidth = r2.getWidth();
            textHeight = r2.getHeight();

            // Calculate the position for the key instruction text centered beneath scoreText
            double keyInstructionY = scoreTextY + textHeight + 30; // Add some spacing below the score
            double keyInstructionX = (width - textKeyWidth) / 2;
            g2.drawString(textKey, (int) keyInstructionX, (int) (keyInstructionY + fm.getAscent()));

           sound.soundGameOver();

        }


    }

    private void render() {
        // Get the graphics context of the component and draw the buffered image to the screen
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose(); // Dispose of the graphics context to free up resources
    }

    // Method to pause the game loop for a specified duration (in milliseconds)
    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException exception) {
            System.err.println(exception); // Print any interruption errors to the console
        }
    }
}

