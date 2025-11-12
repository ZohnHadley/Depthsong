package game.JFrame_UI.customGameUi.screens.view_company_stats;

import game.JFrame_UI.customGameUi.components.CustomUiComponent;
import game.models.Corporation;
import game.services.ServiceCorporation;
import game.services.ServicePlayer;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Objects;


public class Component_SelectedCompanyStats extends CustomUiComponent {
    private final Container parentComponent;
    private JPanel containerPanel;
    private ServicePlayer servicePlayer;
    private ServiceCorporation serviceCompany;
    private HashMap<String, String> corporationStats;

    private JPanel header;
    private ImageIcon imageIcon_corporationLogo;
    private JLabel corporationLogo;

    private JLabel label_corporationName;

    @Getter
    @Setter
    private Corporation selectedCorporation;
    @Getter
    @Setter
    private int width = 200, height = 200;
    //TODO fix this
        //timing from interface and game time are to different;
        //not sure but it might causing errors in stats update (not getting data on time)
    public Component_SelectedCompanyStats(Container param_parentComponent) {
        parentComponent = param_parentComponent;
        selectedCorporation = null;
        servicePlayer = ServicePlayer.getInstance();
        serviceCompany = ServiceCorporation.getInstance();
        ///
        containerPanel = new JPanel();
        containerPanel.setSize(width, height);
        containerPanel.setBackground(new java.awt.Color(12, 12, 82));
        containerPanel.setOpaque(true);

        label_corporationName = new JLabel("---");


        corporationLogo = new JLabel("");
    }

    @Override
    public Container init(){

        header = new JPanel();
        header.setLayout(new FlowLayout());
        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setOpaque(true);


        header.add(corporationLogo);
        header.add(label_corporationName);
        containerPanel.add(header);

        return  containerPanel;
    }

    @Override
    public void update(){
        if(selectedCorporation == null){
            return;
        }
        String corporationName = selectedCorporation.getCompanyName();
        label_corporationName.setText(corporationName);
        //TODO: fix this
        imageIcon_corporationLogo = new ImageIcon(
                Objects.requireNonNull(getClass().getResource(selectedCorporation.getCorporationLogoSrc()))
        );
        imageIcon_corporationLogo.setImage(imageIcon_corporationLogo.getImage().getScaledInstance(
                32, 32, Image.SCALE_SMOOTH
        ));
        corporationLogo.setIcon(imageIcon_corporationLogo);

//        String capital = String.format("%.2f", selectedCorporation.getCapital());
//        String income = String.format("%.2f", selectedCorporation.getIncome());
//        String revenu = String.format("%.2f", selectedCorporation.getRevenu());
//        String expenses = String.format("%.2f", selectedCorporation.getExpenses());
//
//
//        corporationLabel.setText("1# Companie:" + selectedCorporation.getCompanyName());
//        corporationCapitalLabel.setText("Capital: " + capital + " $");
//        corporationIncomeLabel.setText("incomee: " + income + " $");
//        corporationRevenueLabel.setText("revenu: " + revenu + " $");
//        corporationExpensesLabel.setText("Expenses: " + expenses + " $");
        containerPanel.repaint();
//        containerPanel.revalidate();
    }

    @Override
    public void dispose(){
        containerPanel.removeAll();
    }
}
