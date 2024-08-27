package game.obj;

/**
 * The ModelBoom class represents the model of a boom (explosion) effect in the game.
 * It contains information about the size and angle of the explosion.
 */
public class ModelBoom {
    // The size of the explosion effect
    private double size;
    // The angle at which the explosion effect is oriented
    private float angle;

    /**
     * Constructor to create a ModelBoom with specified size and angle.
     *
     * @param size  The size of the explosion effect.
     * @param angle The angle of the explosion effect.
     */
    public ModelBoom(double size, float angle) {
        this.size = size;
        this.angle = angle;
    }

    /**
     * Default constructor to create a ModelBoom with no initial size or angle.
     */
    public ModelBoom() {
        // No initialization, size and angle will be set later
    }

    /**
     * Gets the size of the explosion effect.
     *
     * @return The size of the explosion effect.
     */
    public double getSize() {
        return size;
    }

    /**
     * Sets the size of the explosion effect.
     *
     * @param size The size to set for the explosion effect.
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Gets the angle of the explosion effect.
     *
     * @return The angle of the explosion effect.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the explosion effect.
     *
     * @param angle The angle to set for the explosion effect.
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }
}
