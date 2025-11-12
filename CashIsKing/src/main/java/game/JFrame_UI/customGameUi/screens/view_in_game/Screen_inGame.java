package game.JFrame_UI.customGameUi.screens.view_in_game;

import game.JFrame_UI.customGameUi.components.Component_MenuBar;
import game.JFrame_UI.customGameUi.screens.ICustomUiScreen;
import game.JFrame_UI.customGameUi.screens.view_company_stats.Component_SelectedCompanyStats;
import game.JFrame_UI.customGameUi.screens.view_company_stats.Screen_CompanyStats;
import game.JFrame_UI.customGameUi.screens.view_markets.Screen_Markets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen_inGame extends ICustomUiScreen {
    private Container parentComponent;
    private JPanel containerPanel;

    private ICustomUiScreen screen_companyStats;
    private ICustomUiScreen screen_markets;
    private Component_SelectedCompanyStats component_companyStats;
    private Component_MenuBar component_menuBar;

    private ICustomUiScreen currentScreen;
    private ICustomUiScreen prevScreen;

    public Screen_inGame(Container param_parentComponent ){
        this.parentComponent = param_parentComponent;
        containerPanel = new JPanel();
        containerPanel.setSize(parentComponent.getWidth(), parentComponent.getHeight());
        containerPanel.setLayout(new BorderLayout());
        component_menuBar = new Component_MenuBar(containerPanel);

        screen_companyStats = new Screen_CompanyStats(containerPanel);
        screen_markets = new Screen_Markets(containerPanel);

        currentScreen = screen_companyStats;
        prevScreen = currentScreen;
    }

    @Override
    public Container init() {
        component_menuBar.addButton("Company data");
        component_menuBar.getButton("Company data").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prevScreen = currentScreen;
                currentScreen = screen_companyStats;
                switchScreen();
            }
        });
        component_menuBar.addButton("Market");
        component_menuBar.getButton("Market").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prevScreen = currentScreen;
                currentScreen = screen_markets;
                switchScreen();
            }
        });
        component_menuBar.addButton("Other companies");
        component_menuBar.getButton("Other companies").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prevScreen = currentScreen;
                switchScreen();
            }
        });

        containerPanel.add(component_menuBar.init(), BorderLayout.NORTH);
        containerPanel.add(currentScreen.init(), BorderLayout.CENTER);

//        parentComponent.addComponentListener(new java.awt.event.ComponentAdapter() {
//            @Override
//            public void componentResized(java.awt.event.ComponentEvent evt) {
//                containerPanel.setSize(parentComponent.getWidth(), parentComponent.getHeight());
//            }
//        });
        return containerPanel;
    }

    private void switchScreen(){
        if(prevScreen != currentScreen) {
            containerPanel.remove(prevScreen.init());
            prevScreen = currentScreen;
            containerPanel.add(currentScreen.init(), BorderLayout.CENTER);
        }
    }

    @Override
    public void update() {
        component_menuBar.update();
        currentScreen.update();
        containerPanel.repaint();
        containerPanel.revalidate();
    }
    
}
