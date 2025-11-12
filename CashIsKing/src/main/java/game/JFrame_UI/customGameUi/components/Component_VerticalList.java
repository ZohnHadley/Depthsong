package game.JFrame_UI.customGameUi.components;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Component_VerticalList<T> extends CustomUiComponent {
    private final Container parentComponent;
    private HashMap<String, T> items;
    private JPanel containerPanel;
    private JList<String> jList;
    private DefaultListModel<String> listModel;

    @Getter
    @Setter
    private int width = 25, height = 25 ;
    @Getter
    private T selectedItem;
    public Component_VerticalList(Container parentComponent) {
        items = new HashMap<>();
        selectedItem = null;
        this.parentComponent = parentComponent;
        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        jList = new JList<>();
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    public void addItem(String name, T item) {
        if (items.containsKey(name)) {
            System.err.println(
                    "Item with name " + name + " already exists. Please use a different name or remove the existing item first."
            );
            return;
        }
        if (listModel == null) {
            listModel = new DefaultListModel<>();
        }
        items.put(name, item);
        listModel.addElement(name);
    }

    public void removeItem(String name) {
        if (items.containsKey(name)) {
            T item = items.remove(name);
            listModel.removeElement(name);
        } else {
            throw new IllegalArgumentException("Item with name " + name + " does not exist");
        }

    }

    @Override
    public Container init(){
        containerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jList.setModel(listModel);
        jList.setLayoutOrientation(JList.VERTICAL);
        jList.setSize(width,height);
        jList.setFixedCellWidth(width);
        //add spacing between items
        jList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
              JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
              label.setBorder(BorderFactory.createEmptyBorder(1, 12, 1, 5)); // Add padding around each item
              return label;
            }
        });
        containerPanel.add(jList);
        return containerPanel;
    }

    @Override
    public void update(){
        selectedItem = items.get(jList.getSelectedValue());
        containerPanel.repaint();
//        System.out.println("Selected item: " + (selectedItem != null ? selectedItem.toString() : "null"));
//        containerPanel.revalidate();
    }

    @Override
    public void dispose(){
        jList = null;
        containerPanel.removeAll();
    }

}
