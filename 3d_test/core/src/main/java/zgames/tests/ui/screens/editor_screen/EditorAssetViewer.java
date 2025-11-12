package zgames.tests.ui.screens.editor_screen;

import com.badlogic.gdx.Gdx;
import imgui.ImVec2;
import imgui.flag.ImGuiDir;
import imgui.flag.ImGuiDockNodeFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;
import imgui.internal.ImGuiDockNode;
import imgui.type.ImInt;

public class EditorAssetViewer {
    private final int CUSTOM_DOCK_NODE_FLAGS = ImGuiWindowFlags.NoMove | ImGuiDockNodeFlags.AutoHideTabBar | ImGuiWindowFlags.NoCollapse;

    protected void show(){
        int docker_space_id = ImGui.getID("AssetViewer");
        ImGui.dockBuilderAddNode(docker_space_id);
        //position and size of the docker space
        ImGui.dockBuilderSetNodePos(docker_space_id, new ImVec2(0, 0));
        ImGui.dockBuilderSetNodeSize(docker_space_id, new ImVec2(Gdx.graphics.getWidth(), ImGui.getWindowHeight()));
//
        imgui.type.ImInt leftDockerId = new ImInt(0);
        imgui.type.ImInt rightDockerId = new ImInt(0);
        ImGui.dockBuilderSplitNode(docker_space_id, ImGuiDir.Left, 0.1f, leftDockerId, rightDockerId);
//
        ImGuiDockNode leftNode = ImGui.dockBuilderGetNode(leftDockerId.get());
        ImGuiDockNode rightNode = ImGui.dockBuilderGetNode(rightDockerId.get());

        ImGui.dockBuilderDockWindow("assets", leftNode.getID());
        ImGui.dockBuilderDockWindow("asset_files", rightNode.getID());

        ImGui.dockBuilderFinish(docker_space_id);


        ImGui.dockSpace(docker_space_id, new ImVec2(0, 0), ImGuiDockNodeFlags.AutoHideTabBar);
        ImGui.begin("assets", CUSTOM_DOCK_NODE_FLAGS);
        ImGui.text("assets");

        ImGui.treeNodeEx( "GameObjects", ImGuiTreeNodeFlags.Leaf | ImGuiTreeNodeFlags.NoTreePushOnOpen, "GameObjects");
        if (ImGui.isMouseClicked(0) && ImGui.isItemClicked()) {
            System.out.println("load GameObjects");
        }
        ImGui.treeNodeEx( "Models", ImGuiTreeNodeFlags.Leaf | ImGuiTreeNodeFlags.NoTreePushOnOpen, "Models");
        if (ImGui.isMouseClicked(0) && ImGui.isItemClicked()) {
            System.out.println("load Models Folders");
        }
        ImGui.treeNodeEx( "Textures", ImGuiTreeNodeFlags.Leaf | ImGuiTreeNodeFlags.NoTreePushOnOpen, "Textures");
        if (ImGui.isMouseClicked(0) && ImGui.isItemClicked()) {
            System.out.println("load Textures");
        }
        ImGui.treeNodeEx( "Sounds", ImGuiTreeNodeFlags.Leaf | ImGuiTreeNodeFlags.NoTreePushOnOpen, "Sounds");
        if (ImGui.isMouseClicked(0) && ImGui.isItemClicked()) {
            System.out.println("load Sounds");
        }
 
        ImGui.end();

        ImGui.begin("asset_files", CUSTOM_DOCK_NODE_FLAGS);
        ImGui.text("asset_folders");

        ImGui.end();

    }
}
