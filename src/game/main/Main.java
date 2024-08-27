/**
 * Main class for the Star Galaxy game.
 * This class sets up the main JFrame window for the game and initializes the game panel.
 * The game is a remake created by Michael Kane.
 *
 * The game window is configured with a fixed size, title, and non-resizable properties.
 * Upon opening the window, the game starts automatically.
 *
 * Author: Michael Kane
 */

package game.main;

import game.component.PanelGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {

    // Constructor for the Main class
    public Main() {
        init(); // Initialize the game window
    }

    // Method to initialize the game window and its properties
    private void init() {
        setTitle("Star Galaxy"); // Set the title of the game window
        setSize(1366, 768); // Set the size of the game window
        setLocationRelativeTo(null); // Center the window on the screen
        setResizable(false); // Prevent the window from being resized
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        setLayout(new BorderLayout()); // Set the layout manager for the window

        PanelGame panelGame = new PanelGame(); // Create a new game panel
        add(panelGame); // Add the game panel to the window

        // Add a window listener to start the game when the window is opened
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panelGame.start(); // Start the game
            }
        });
    }

    // Main method to launch the game application
    public static void main(String[] args) {
        Main main = new Main(); // Create an instance of the Main class
        main.setVisible(true); // Make the game window visible
    }
}
