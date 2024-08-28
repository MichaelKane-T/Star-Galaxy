package game.component;

/**
 * The Key class manages the state of various keyboard keys.
 * It provides methods to check and set the status of keys used for controlling the game.
 */
public class Key {

    // Key state attributes
    private boolean key_right; // State of the right arrow key
    private boolean key_left;  // State of the left arrow key
    private boolean key_j;     // State of the 'J' key
    private boolean key_k;     // State of the 'K' key
    private boolean key_space; // State of the spacebar key
    private boolean key_enter; // State of the enter key

    /**
     * Checks if the right arrow key is pressed.
     *
     * @return true if the right arrow key is pressed, false otherwise.
     */
    public boolean isKey_right() {
        return key_right;
    }

    /**
     * Sets the state of the right arrow key.
     *
     * @param key_right true if the right arrow key is pressed, false otherwise.
     */
    public void setKey_right(boolean key_right) {
        this.key_right = key_right;
    }

    /**
     * Checks if the enter key is pressed.
     *
     * @return true if the enter key is pressed, false otherwise.
     */
    public boolean isKey_enter() {
        return key_enter;
    }

    /**
     * Sets the state of the enter  key.
     *
     * @param key_enter true if the right arrow key is pressed, false otherwise.
     */
    public void setKey_enter(boolean key_enter) {
        this.key_enter = key_enter;
    }

    /**
     * Checks if the spacebar key is pressed.
     *
     * @return true if the spacebar key is pressed, false otherwise.
     */
    public boolean isKey_space() {
        return key_space;
    }

    /**
     * Sets the state of the spacebar key.
     *
     * @param key_space true if the spacebar key is pressed, false otherwise.
     */
    public void setKey_space(boolean key_space) {
        this.key_space = key_space;
    }

    /**
     * Checks if the left arrow key is pressed.
     *
     * @return true if the left arrow key is pressed, false otherwise.
     */
    public boolean isKey_left() {
        return key_left;
    }

    /**
     * Sets the state of the left arrow key.
     *
     * @param key_left true if the left arrow key is pressed, false otherwise.
     */
    public void setKey_left(boolean key_left) {
        this.key_left = key_left;
    }

    /**
     * Checks if the 'J' key is pressed.
     *
     * @return true if the 'J' key is pressed, false otherwise.
     */
    public boolean isKey_j() {
        return key_j;
    }

    /**
     * Sets the state of the 'J' key.
     *
     * @param key_j true if the 'J' key is pressed, false otherwise.
     */
    public void setKey_j(boolean key_j) {
        this.key_j = key_j;
    }

    /**
     * Checks if the 'K' key is pressed.
     *
     * @return true if the 'K' key is pressed, false otherwise.
     */
    public boolean isKey_k() {
        return key_k;
    }

    /**
     * Sets the state of the 'K' key.
     *
     * @param key_k true if the 'K' key is pressed, false otherwise.
     */
    public void setKey_k(boolean key_k) {
        this.key_k = key_k;
    }
}
