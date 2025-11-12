package game.JFrame_UI.customGameUi.screens.view_company_stats;

import game.JFrame_UI.customGameUi.components.Component_VerticalList;
import game.JFrame_UI.customGameUi.screens.ICustomUiScreen;
import game.models.Corporation;
import game.services.ServicePlayer;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class Screen_CompanyStats extends ICustomUiScreen {
    private ServicePlayer servicePlayer;
    @Getter
    private String title = "screen_company_stats";
    private final Container parentComponent;
    private JPanel containerPanel;

    Component_SelectedCompanyStats component_selectedCompanyStats;
    Component_VerticalList<Corporation> component_verticalList;

    public Screen_CompanyStats(Container parentComponent) {
        servicePlayer = ServicePlayer.getInstance();
        this.parentComponent = parentComponent;
        containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        containerPanel.setOpaque(false);
        containerPanel.setSize( parentComponent.getWidth(), parentComponent.getHeight());

        component_selectedCompanyStats  = new Component_SelectedCompanyStats(parentComponent);
        component_selectedCompanyStats.setWidth(200);
        component_selectedCompanyStats.setHeight(200);

        component_verticalList = new Component_VerticalList<>(parentComponent);
        component_verticalList.setWidth(120);
        component_verticalList.setHeight(200);
        for (Corporation corporation : servicePlayer.getPlayer().getCompanies()) {
            System.out.println("Adding company: " + corporation.getCompanyName());
            component_verticalList.addItem(corporation.getTicker()+" | "+ corporation.getCompanyName(), corporation);
        }

        containerPanel.add(component_verticalList.init(), BorderLayout.WEST,0);
        containerPanel.add(component_selectedCompanyStats.init(), BorderLayout.CENTER,1);
    }

    @Override
    public Container init() {
        return containerPanel;
    }

    @Override
    public void update() {
        component_selectedCompanyStats.update();
        component_verticalList.update();
        component_selectedCompanyStats.setSelectedCorporation(component_verticalList.getSelectedItem());
        containerPanel.repaint();
        containerPanel.revalidate();
    }

    @Override
    public void dispose() {
        component_selectedCompanyStats.dispose();
        containerPanel.removeAll();
    }
}
