package game.obj;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

/**
 * The Player class represents the player's character in the game.
 * It handles the player's position, movement, and rendering.
 */
public class Player extends HpRender{

    // Constants
    public static final double PLAYER_SIZE = 64; // Size of the player (width/height)
    private final float MAX_SPEED = 1f; // Maximum speed the player can reach

    // Player attributes
    private double x; // x-coordinate of the player
    private double y; // y-coordinate of the player
    private float speed = 0f; // Current speed of the player
    private boolean speedUp; // Whether the player is speeding up
    private boolean alive = true; //Whether the player is alive or not
    private float angle = 0f; // Current angle of the player

    // Images for the player
    private final Image image; // Default image of the player
    private final Image image_speed; // Image of the player when speeding up
    private final Area  playerShape;

    /**
     * Constructor to initialize the Player object and load images.
     */
    public Player() {

        // Load the player's default and speed-up images from the resources
        super(new HP(30,40));
        this.image = new ImageIcon(getClass().getResource("/game/image/plane.png")).getImage();
        image_speed = new ImageIcon(getClass().getResource("/game/image/plane_speed.png")).getImage();
        Path2D p = new Path2D.Double();
        p.moveTo(0,15);
        p.lineTo(20,5);
        p.lineTo(PLAYER_SIZE +15,PLAYER_SIZE/2);
        p.lineTo(20,PLAYER_SIZE -5);
        p.lineTo(0,PLAYER_SIZE-15);
        p.closePath();
        playerShape =new Area(p);
    }

    /**
     * Changes the location of the player.
     *
     * @param x The new x-coordinate of the player.
     * @param y The new y-coordinate of the player.
     */
    public void changeLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the position of the player based on the current speed and angle.
     * This method is called in each frame of the game to animate the player's movement.
     */
    public void update() {
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }

    /**
     * Changes the angle (direction) the player is facing.
     *
     * @param angle The new angle of the player in degrees.
     */
    public void changeAngel(float angle) {
        if (angle < 0) {
            angle = 359;
        } else if (angle > 359) {
            angle = 0;
        }
        this.angle = angle;
    }

    /**
     * Draws the player on the screen.
     *
     * @param g2 The Graphics2D object used for drawing the player.
     */
    public void draw(Graphics2D g2) {
        // Save the original transformation
        AffineTransform oldTransform = g2.getTransform();

        // Translate the coordinate system to the player's position
        g2.translate(x, y);

        // Rotate the player based on the current angle
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 45), PLAYER_SIZE / 2, PLAYER_SIZE / 2);

        // Draw the player image (use the speed-up image if the player is speeding up)
        g2.drawImage(speedUp ? image_speed : image, tran, null);
        hpRender(g2,getShape(),y);
        // Restore the original transformation
        g2.setTransform(oldTransform);

        //Test
//        g2.setColor(new Color(12,173,84));
//        g2.draw(getShape());
//        g2.draw(getShape().getBounds());
    }

    /**
     * Gets the current x-coordinate of the player.
     *
     * @return The x-coordinate of the player.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate of the player.
     *
     * @return The y-coordinate of the player.
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the current angle (direction) the player is facing.
     *
     * @return The angle of the player in degrees.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Increases the player's speed, simulating a speed-up.
     * If the speed exceeds the maximum speed, it is capped at MAX_SPEED.
     */
    public void speedUp() {
        speedUp = true;
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        } else {
            speed += 0.01f; // Gradually increase the speed
        }
    }
    public Area getShape() {
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle), PLAYER_SIZE / 2, PLAYER_SIZE / 2);
        return new Area(afx.createTransformedShape(playerShape));
    }


    /**
     * Decreases the player's speed, simulating a speed-down.
     * The speed is gradually reduced, but it cannot go below 0.
     */
    public void speedDown() {
        speedUp = false;
        if (speed <= 0) {
            speed = 0;
        } else {
            speed -= 0.003f; // Gradually decrease the speed
        }
    }
    /**
     * Gets the current Status (dead/alive) of the player.
     *
     * @return The Status of the player in boolean.
     */
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    public void reset() {
        alive = true;
        resetHP();
        angle = 0;
        speed = 0;

    }
}
