package game.obj;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

/**
 * The Rocket class represents a rocket entity in the game.
 * It handles the rocket's position, movement, rendering, and collision detection.
 * The rocket has a defined shape and an associated image, and it inherits
 * from the HpRender class to manage and display its health points (HP).
 */
public class Rocket extends HpRender {
    // Constants
    public static final double ROCKET_SIZE = 50;  // Size of the rocket

    // Rocket properties
    private double x;  // X-coordinate of the rocket's position
    private double y;  // Y-coordinate of the rocket's position
    private final float speed = 0.3f;  // Speed of the rocket
    private float angle = 0;  // Angle of the rocket's movement in degrees
    private final Image image;  // Image representing the rocket
    private final Area rocketShape;  // Shape of the rocket for collision detection

    /**
     * Constructor for the Rocket class.
     * Initializes the rocket's HP and sets up its shape and image.
     */
    public Rocket() {
        super(new HP(20, 20));  // Initialize rocket's HP with maximum and current HP of 20
        this.image = new ImageIcon(getClass().getResource("/game/image/rocket.png")).getImage();  // Load rocket image

        // Define the shape of the rocket using a Path2D object
        Path2D path = new Path2D.Double();
        path.moveTo(0, ROCKET_SIZE / 2);
        path.lineTo(15, 10);
        path.lineTo(ROCKET_SIZE - 5, 13);
        path.lineTo(ROCKET_SIZE + 10, ROCKET_SIZE / 2);
        path.lineTo(ROCKET_SIZE - 5, ROCKET_SIZE - 13);
        path.lineTo(15, ROCKET_SIZE - 10);
        path.closePath();  // Close the path to complete the shape
        rocketShape = new Area(path);  // Assign the path to the rocketShape
    }

    /**
     * Updates the rocket's position based on its current angle and speed.
     */
    public void update() {
        x += Math.cos(Math.toRadians(angle)) * speed;  // Update X-coordinate
        y += Math.sin(Math.toRadians(angle)) * speed;  // Update Y-coordinate
    }

    /**
     * Changes the location of the rocket to a new X and Y position.
     * @param x The new X-coordinate of the rocket.
     * @param y The new Y-coordinate of the rocket.
     */
    public void changeLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adjusts the rocket's angle of movement.
     * If the angle goes beyond the bounds (0-359 degrees), it wraps around.
     * @param angle The new angle in degrees.
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
     * Renders the rocket on the screen.
     * The rocket is drawn at its current position and angle.
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {
        // Save the current transformation
        AffineTransform oldTransform = g2.getTransform();

        // Move to the rocket's position
        g2.translate(x, y);

        // Rotate the rocket image based on its angle
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle + 45), ROCKET_SIZE / 2, ROCKET_SIZE / 2);
        g2.drawImage(image, tran, null);  // Draw the rocket image

        // Render the rocket's HP bar
        Shape shape = getRocketShape();
        hpRender(g2, shape, y);

        // Restore the original transformation
        g2.setTransform(oldTransform);

//        Debug code to visualize the rocket's shape and bounding box
//        g2.setColor(Color.green);
//        g2.draw(shape);
//        g2.draw(shape.getBounds2D());
    }

    /**
     * Gets the current X-coordinate of the rocket.
     * @return The X-coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the current Y-coordinate of the rocket.
     * @return The Y-coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * Gets the current angle of the rocket.
     * @return The angle in degrees.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Returns the transformed shape of the rocket, taking into account its position and angle.
     * This is used for collision detection.
     * @return The transformed shape of the rocket.
     */
    public Area getRocketShape() {
        AffineTransform afx = new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle), ROCKET_SIZE / 2, ROCKET_SIZE / 2);
        return new Area(afx.createTransformedShape(rocketShape));
    }

    /**
     * Checks whether the rocket is within the screen bounds.
     * If the rocket moves outside the screen, it returns false.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @return true if the rocket is within the screen bounds, false otherwise.
     */
    public boolean check(int width, int height) {
        Rectangle size = getRocketShape().getBounds();
        if (x <= -size.getWidth() || y < -size.getHeight() || x > width || y > height) {
            return false;
        } else {
            return true;
        }
    }
}
