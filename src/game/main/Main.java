package game.main;

import game.component.PanelGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame {
    //constructor
    public Main(){
        init();
    }
    //Method
    private void init(){
        setTitle("Star Galaxy");
        setSize(1366,768);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//stops the programme
        setLayout(new BorderLayout());
        PanelGame panelGame = new PanelGame();
        add(panelGame);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panelGame.start();
            }
        });

    }

    //Main Method
    public static void main(String[] args){
        Main main = new Main();
        main.setVisible(true);

    }
}
