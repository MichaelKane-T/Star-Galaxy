package game.obj.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * The Sound class is responsible for managing and playing various sound effects in the game.
 */
public class Sound {
    // URLs to the sound files for different game actions
    private final URL shoot;
    private final URL hit;
    private final URL destroy;
    private final URL gameOver;
    private final URL gameStart;
    private boolean gameOverPlayed = false; // Flag to track if game over sound has played

    // Clip object to control playback of sound
    private Clip clip;

    /**
     * Constructor to initialize sound URLs by loading them from the resources folder.
     */
    public Sound() {
        this.shoot = this.getClass().getClassLoader().getResource("game/obj/sound/laserGunShoot.wav");
        this.hit = this.getClass().getClassLoader().getResource("game/obj/sound/hit.wav");
        this.destroy = this.getClass().getClassLoader().getResource("game/obj/sound/explosion.wav");
        this.gameOver = this.getClass().getClassLoader().getResource("game/obj/sound/gameOver.wav");
        this.gameStart = this.getClass().getClassLoader().getResource("game/obj/sound/gameStart.wav");
    }

    /**
     * Plays the shooting sound effect.
     */
    public void soundShoot() {
        play(shoot);
    }

    /**
     * Plays the hit sound effect.
     */
    public void soundHit() {
        play(hit);
    }

    /**
     * Plays the destroy sound effect.
     */
    public void soundDestroy() {
        play(destroy);
    }

    /**
     * Plays the game over sound effect, ensuring it plays only once.
     */
    public void soundGameOver() {
        if (!clip.isRunning() && !gameOverPlayed) {
            playOnce(gameOver);
            gameOverPlayed = true; // Mark the sound as played
        }
    }

    public void resetGameOverFlag() {
        gameOverPlayed = false; // Call this when you want to allow the sound to be played again
    }


    /**
     * Plays the game start sound effect.
     */
    public void soundGameStart() {
        play(gameStart);
    }

    /**
     * Plays a sound from a given URL, stopping any currently playing sound before starting a new one.
     *
     * @param url The URL of the sound file to play.
     */
    private void play(URL url) {
        try {
            // Stop the current sound if it is still playing
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            // Load the sound file
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a new Clip instance
            clip = AudioSystem.getClip();
            // Open the Clip and start playing the sound
            clip.open(audioIn);
            clip.start();
            // Close the AudioInputStream to release resources
            audioIn.close();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            // Print any exceptions that occur
            System.err.println(e);
        }
    }

    /**
     * Plays a sound from a given URL only once, ensuring that the Clip is closed after playing.
     *
     * @param url The URL of the sound file to play.
     */
    private void playOnce(URL url) {
        try {
            // Stop the current sound if it is still playing
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            // Load the sound file
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            // Get a new Clip instance
            clip = AudioSystem.getClip();
            // Open the Clip
            clip.open(audioIn);
            // Set the Clip to play only once
            clip.loop(0);
            // Start playing the sound
            clip.start();
            // Add a listener to close the Clip after the sound has finished playing
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            // Close the AudioInputStream to release resources
            audioIn.close();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            // Print any exceptions that occur
            System.err.println(e);
        }
    }
}
