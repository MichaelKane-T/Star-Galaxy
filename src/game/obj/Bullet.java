package game.obj;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * The Bullet class represents a bullet object in the game.
 * It handles the position, movement, and rendering of the bullet.
 */
public class Bullet {

    // Position of the bullet
    private double x;
    private double y;

    // Shape of the bullet (an ellipse)
    private final Shape shape;

    // Color of the bullet
    private final Color color = new Color(255, 255, 255);

    // Angle at which the bullet is fired
    private final float angle;

    // Size of the bullet (diameter)
    private final double size;

    // Speed of the bullet
    private float speed = 1f;

    /**
     * Constructor to initialize the Bullet object with specified parameters.
     *
     * @param x     The initial x-coordinate of the bullet.
     * @param y     The initial y-coordinate of the bullet.
     * @param angle The angle at which the bullet is fired.
     * @param size  The size (diameter) of the bullet.
     * @param speed The speed at which the bullet moves.
     */
    public Bullet(double x, double y, float angle, double size, float speed) {
        // Adjust the initial position so that the bullet starts from the center of the player
        x += Player.PLAYER_SIZE / 2 - (size / 2);
        y += Player.PLAYER_SIZE / 2 - (size / 2);
        this.x = x;
        this.y = y;
        this.size = size;
        this.angle = angle;
        this.speed = speed;

        // Define the shape of the bullet as an ellipse
        shape = new Ellipse2D.Double(0, 0, size, size);
    }

    /**
     * Updates the position of the bullet based on its speed and angle.
     * This method is called in each frame of the game to animate the bullet.
     */
    public void update() {
        x += Math.cos(Math.toRadians(angle)) * speed;
        y += Math.sin(Math.toRadians(angle)) * speed;
    }

    /**
     * Checks whether the bullet is out of bounds (off-screen).
     *
     * @param width  The width of the game screen.
     * @param height The height of the game screen.
     * @return true if the bullet is out of bounds, false otherwise.
     */
    public boolean check(int width, int height) {
        if (x <= -size || y < -size || x > width || y > height) {
            return true; // Bullet is out of bounds
        } else {
            return false; // Bullet is still within the screen bounds
        }
    }

    /**
     * Draws the bullet on the screen.
     *
     * @param g2 The Graphics2D object used for drawing the bullet.
     */
    public void draw(Graphics2D g2) {
        // Save the original transformation
        AffineTransform oldTransform = g2.getTransform();

        // Set the color and translate the coordinate system to the bullet's position
        g2.setColor(color);
        g2.translate(x, y);

        // Draw the bullet (an ellipse)
        g2.fill(shape);

        // Restore the original transformation
        g2.setTransform(oldTransform);
    }

    /**
     * Gets the current x-coordinate of the bullet.
     *
     * @return The x-coordinate of the bullet.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate of the bullet.
     *
     * @return The y-coordinate of the bullet.
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the size (diameter) of the bullet.
     *
     * @return The size of the bullet.
     */
    public double getSize() {
        return size;
    }

    /**
     * Gets the x-coordinate of the bullet's center.
     *
     * @return The x-coordinate of the bullet's center.
     */
    public double getCenterX() {
        return x + size / 2;
    }

    /**
     * Gets the y-coordinate of the bullet's center.
     *
     * @return The y-coordinate of the bullet's center.
     */
    public double getCenterY() {
        return y + size / 2;
    }

    /**
     * Gets the shape of the bullet as an Area object for collision detection.
     *
     * @return The shape of the bullet.
     */
    public Shape getShape() {
        return new Area(new Ellipse2D.Double(x, y, size, size));
    }
}
