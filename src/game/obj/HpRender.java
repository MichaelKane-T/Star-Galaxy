package game.obj;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * The HpRender class is responsible for rendering the health points (HP) of a game entity.
 * It uses an instance of the HP class to track and display the current health status of the entity.
 * The class provides methods to render the HP bar, update the HP, retrieve the current HP,
 * and reset the HP to its maximum value.
 *
 */
public class HpRender {
    // Instance of HP class to manage the health points of the entity
    private final HP hp;

    /**
     * Constructor to initialize HpRender with a specific HP instance.
     * @param hp The HP object representing the health points to be rendered.
     */
    public HpRender(HP hp) {
        this.hp = hp;
    }

    /**
     * Renders the HP bar above the entity's shape on the screen.
     * The HP bar is displayed as a small rectangle indicating the current health.
     *
     * @param g2 The Graphics2D object used for drawing the HP bar.
     * @param shape The shape of the entity whose HP is being rendered.
     * @param y The vertical offset for positioning the HP bar.
     */
    protected void hpRender(Graphics2D g2, Shape shape, double y) {
        if (hp.getCurrentHP()!=hp.getMAX_HP()){
        // Calculate the Y position for the HP bar based on the entity's shape
        double hpY = shape.getBounds().getY() - y - 10;
        // Draw the background of the HP bar (grey color)
        g2.setColor(new Color(70, 70, 70));
        g2.fill(new Rectangle2D.Double(0, hpY, Player.PLAYER_SIZE, 2));

        // Draw the current HP bar (red color) based on the current HP percentage
        g2.setColor(new Color(253, 91, 91));
        double hpSize = hp.getCurrentHP() / hp.getMAX_HP() * Player.PLAYER_SIZE;
        g2.fill(new Rectangle2D.Double(0, hpY, hpSize, 2));
        }
    }

    /**
     * Updates the current HP by reducing it by a specified amount.
     *
     * @param cutHP The amount of HP to be deducted.
     * @return true if the entity is still alive (HP > 0), false if the entity is dead (HP <= 0).
     */
    public boolean updateHP(double cutHP) {
        hp.setCurrentHP(hp.getCurrentHP() - cutHP);
        return hp.getCurrentHP() > 0;
    }

    /**
     * Retrieves the current health points.
     *
     * @return The current HP value.
     */
    public double getHp() {
        return hp.getCurrentHP();
    }

    /**
     * Resets the current HP to the maximum HP value.
     */
    public void resetHP() {
        hp.setCurrentHP(hp.getMAX_HP());
    }
}
