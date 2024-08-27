package game.obj;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * The Effect class represents a visual explosion effect in the game.
 * It handles the rendering and updating of explosion particles, which spread out from a central point.
 */
public class Effect {
    // Coordinates of the explosion effect's origin
    private final double x;
    private final double y;

    // Maximum distance the particles will travel from the origin
    private final double max_distance;

    // Maximum size of the explosion particles
    private final int max_size;

    // Color of the explosion particles
    private final Color color;

    // Total number of particles in the explosion
    private final int totalEffect;

    // Speed at which the particles move
    private final float speed;

    // Current distance the particles have traveled
    private double current_distance;

    // Array of ModelBoom objects representing individual particles
    private ModelBoom booms[];

    // Transparency of the particles, used for fading out
    private float alpha = 1f;

    /**
     * Constructor to initialize the Effect object with specified parameters.
     *
     * @param x            The x-coordinate of the explosion's origin.
     * @param y            The y-coordinate of the explosion's origin.
     * @param max_distance The maximum distance the particles will travel.
     * @param max_size     The maximum size of the particles.
     * @param totalEffect  The total number of particles in the explosion.
     * @param speed        The speed at which the particles move.
     * @param color        The color of the particles.
     */
    public Effect(double x, double y, double max_distance, int max_size, int totalEffect, float speed, Color color) {
        this.x = x;
        this.y = y;
        this.max_distance = max_distance;
        this.max_size = max_size;
        this.color = color;
        this.totalEffect = totalEffect;
        this.speed = speed;
        createRandom();  // Initialize the particles with random sizes and angles
    }

    /**
     * Generates random sizes and angles for each particle in the explosion.
     * This method creates a unique pattern for the explosion effect.
     */
    private void createRandom() {
        booms = new ModelBoom[totalEffect];
        float per = 360f / totalEffect;
        Random ran = new Random();
        for (int i = 0; i < totalEffect; i++) {
            int r = ran.nextInt((int) per) + 1;
            int boomSize = ran.nextInt(max_size) + 1;
            float angle = i * per + r;
            booms[i] = new ModelBoom(boomSize, angle);
        }
    }

    /**
     * Draws the explosion effect on the screen.
     *
     * @param g2 The Graphics2D object used for drawing the effect.
     */
    public void draw(Graphics2D g2) {
        AffineTransform oldTransform = g2.getTransform();
        Composite oldComposite = g2.getComposite();
        g2.setColor(color);
        g2.translate(x, y);

        for (ModelBoom b : booms) {
            double bx = Math.cos(Math.toRadians(b.getAngle())) * current_distance;
            double by = Math.sin(Math.toRadians(b.getAngle())) * current_distance;
            double boomSize = b.getSize();
            double space = boomSize / 2;

            // Fade out the particles as they approach the maximum distance
            if (current_distance >= max_distance - (max_distance * 0.7f)) {
                alpha = (float) ((max_distance - current_distance) / (max_distance * 0.7f));
            }

            // Ensure alpha is within valid bounds
            if (alpha > 1) {
                alpha = 1;
            } else if (alpha < 0) {
                alpha = 0;
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(new Rectangle2D.Double(bx - space, by - space, boomSize, boomSize));
        }

        g2.setComposite(oldComposite);
        g2.setTransform(oldTransform);
    }

    /**
     * Updates the state of the explosion effect by moving the particles.
     * This method is called in each frame of the game to animate the effect.
     */
    public void update() {
        current_distance += speed;
    }

    /**
     * Checks whether the explosion effect is still active (i.e., whether the particles
     * have not yet reached the maximum distance).
     *
     * @return true if the effect is still active, false otherwise.
     */
    public boolean check() {
        return current_distance < max_distance;
    }
}
