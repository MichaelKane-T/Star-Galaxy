package game.obj;

/**
 * The HP class represents the health points (HP) of a game entity.
 * It manages both the maximum HP and the current HP, allowing for
 * tracking of health status within the game. This class provides
 * methods to get and set the maximum and current HP values.
 * *
 */
public class HP {
    // Maximum health points of the entity
    private double MAX_HP;

    // Current health points of the entity
    private double currentHP;

    /**
     * Constructor to initialize the HP object with specified maximum and current HP values.
     * @param MAX_HP The maximum health points.
     * @param currentHP The current health points.
     */
    public HP(double MAX_HP, double currentHP) {
        this.MAX_HP = MAX_HP;
        this.currentHP = currentHP;
    }

    /**
     * Default constructor to create an HP object without setting initial values.
     */
    public HP() {
        // No initial values set
    }

    /**
     * Gets the maximum health points.
     * @return The maximum health points.
     */
    public double getMAX_HP() {
        return MAX_HP;
    }

    /**
     * Sets the maximum health points.
     * @param MAX_HP The maximum health points to set.
     */
    public void setMAX_HP(double MAX_HP) {
        this.MAX_HP = MAX_HP;
    }

    /**
     * Sets the current health points.
     * @param currentHP The current health points to set.
     */
    public void setCurrentHP(double currentHP) {
        this.currentHP = currentHP;
    }

    /**
     * Gets the current health points.
     * @return The current health points.
     */
    public double getCurrentHP() {
        return currentHP;
    }
}
