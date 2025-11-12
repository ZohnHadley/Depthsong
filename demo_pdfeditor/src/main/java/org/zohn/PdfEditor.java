package org.zohn;


import javax.swing.*;
import java.awt.*;

public class PdfEditor {

    public static void main(String[] args)
    {
        JFrame frame=new JFrame();
        JPanel top_panel = new JPanel();
        final int[] verticalSize = {250};
        top_panel.setLayout(new BoxLayout(top_panel, BoxLayout.Y_AXIS));
        top_panel.setBounds(0, 0, 500, verticalSize[0]);
        top_panel.setBackground(Color.green);
        JLabel label = new JLabel("PDF Editor");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        top_panel.add(label);
        JButton button = new JButton("Open PDF");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.add(top_panel);
        frame.add(button);
        //on close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //if resizing the frame, then change verticalSize variable
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
