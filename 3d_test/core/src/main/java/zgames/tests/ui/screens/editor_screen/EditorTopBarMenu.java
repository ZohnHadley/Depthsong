package zgames.tests.ui.screens.editor_screen;

import imgui.internal.ImGui;

public class EditorTopBarMenu {
    private EditorTopBarMenu instance;
    private int topMenuHeight;

    protected EditorTopBarMenu getInstance(){
        if(instance == null){
            instance = new EditorTopBarMenu();
        }
        return instance;
    }

    protected int getTopMenuHeight(){
        return topMenuHeight;
    }

    protected void show(){
        ImGui.beginMainMenuBar();
        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Open")) {
                System.out.println("Open");
            }
            if (ImGui.menuItem("Save")) {
                System.out.println("Save");
            }
            ImGui.endMenu();
        }
        topMenuHeight = (int) ImGui.getWindowHeight();
        ImGui.endMainMenuBar();
    }
}
