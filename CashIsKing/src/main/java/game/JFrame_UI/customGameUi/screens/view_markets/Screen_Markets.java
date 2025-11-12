package game.JFrame_UI.customGameUi.screens.view_markets;

import game.JFrame_UI.customGameUi.screens.ICustomUiScreen;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class Screen_Markets extends ICustomUiScreen {
    @Getter
    private String title = "screen_markets";
    private final Container parentComponent;
    private JPanel containerPanel;


    public Screen_Markets(Container parentComponent) {
        this.parentComponent = parentComponent;
        containerPanel = new JPanel();
        containerPanel.setSize(parentComponent.getWidth(), parentComponent.getHeight());
        containerPanel.setLayout(new GridLayout(2, 2));
        containerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        containerPanel.setOpaque(true);
        containerPanel.add(new JLabel("MARKETS"), BorderLayout.CENTER);
    }

    @Override
    public JPanel init() {
        return containerPanel;
    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {
        containerPanel.removeAll();
    }
}
