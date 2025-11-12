package game.JFrame_UI.customGameUi.components;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class Component_MenuBar extends CustomUiComponent {
    private final Container parentComponent;

    private HashMap<String, JButton> buttons;
    private final JPanel containerPanel;
    private Font buttonFont = new Font("Arial", Font.BOLD, 12);

    
    public Component_MenuBar(Container param_parentComponent) {
        buttons  = new HashMap<>();
        parentComponent = param_parentComponent;
        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(
            containerPanel, BoxLayout.X_AXIS
        ));
        containerPanel.setBackground(
            new Color(36, 36, 107, 255) // transparent background
        );
        containerPanel.setSize(parentComponent.getWidth(), parentComponent.getHeight()/16);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        containerPanel.setOpaque(true);

    }

    public void addButton(String name) {
        if (buttons.containsKey(name)) {
            throw new IllegalArgumentException("button with name " + name + " already exists");
        }
        JButton btn = new JButton(name);
        btn.setBackground(new Color(62, 62, 110));
        btn.setForeground(Color.WHITE);
        buttons.put(name, btn);
    }

    public void removeButton(String name, JButton button) {
        buttons.remove(name);
    }

    public JButton getButton(String name) {
        return buttons.get(name);
    }

    @Override
    public Container init(){

        if(!buttons.isEmpty()){
            for(JButton button : buttons.values()){
                button.setFont(buttonFont);
                containerPanel.add(button, BorderLayout.EAST);
            }
        }
        return containerPanel;
    }

    @Override
    public void update(){
    }

    @Override
    public void dispose(){
        buttons.clear();
        containerPanel.removeAll();
    }

}
