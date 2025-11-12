package game.JFrame_UI;

import game.JFrame_UI.customGameUi.screens.ICustomUiScreen;
import game.JFrame_UI.customGameUi.screens.view_in_game.Screen_inGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleInterface{
    private static SimpleInterface instance;
    private JFrame jFrame;
    private int width = 640, height = 500;

    private Screen_inGame inGameScreen;

    private ICustomUiScreen currentScreen;
    private ICustomUiScreen prevScreen;
    Timer timer;

    private SimpleInterface() {
        try {
            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.motif.MotifLookAndFeel" );
            jFrame = new JFrame();
            jFrame.setLocation(100, 100);
            jFrame.setSize(width, height);

            inGameScreen = new Screen_inGame(jFrame);


            currentScreen = inGameScreen;
            prevScreen = currentScreen;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SimpleInterface getInstance() {
        if (instance == null) {
            instance = new SimpleInterface();
        }
        return instance;
    }

    public void initWindow() {
        jFrame.add(currentScreen.init());
        jFrame.setTitle("CashKings");
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        update();
    }

    public void update() {
        timer = new Timer(100, new ActionListener() {
            int count = 10;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (count > 0) {
                    currentScreen.update();
                    jFrame.revalidate();
                    jFrame.repaint();
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }
}
