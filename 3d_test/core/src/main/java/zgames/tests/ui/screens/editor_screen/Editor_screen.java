package zgames.tests.ui.screens.editor_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import imgui.ImGuiViewport;
import imgui.internal.ImGui;
import imgui.ImVec2;
import imgui.flag.*;
import imgui.internal.ImGuiDockNode;
import imgui.type.*;
import zgames.tests.engin_classes.Interfaces.InterfaceSceneInstance;
import zgames.tests.engin_classes.EngineEditorCamera;

public class Editor_screen {


    private final InterfaceSceneInstance sceneInstance;
    private EngineEditorCamera camera;
    private FrameBuffer frameBuffer;
    //

    private ImGuiViewport viewport = ImGui.getMainViewport();
    private final int CUSTOM_WINDOW_FLAGS = ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoScrollbar | ImGuiWindowFlags.NoBringToFrontOnFocus;
    private final int CUSTOM_DOCK_NODE_FLAGS = ImGuiWindowFlags.NoMove | ImGuiDockNodeFlags.AutoHideTabBar | ImGuiWindowFlags.NoCollapse;

    private final EditorTopBarMenu editorTopBarMenu;
    private int topMenuBarHeight;

    private EditorAssetViewer editorAssetViewer;

    public Editor_screen(InterfaceSceneInstance sceneInstance) {

        frameBuffer = new FrameBuffer(Pixmap.Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        this.sceneInstance = sceneInstance;
        this.camera = EngineEditorCamera.getInstance();
        this.editorTopBarMenu = new EditorTopBarMenu();

        this.editorAssetViewer = new EditorAssetViewer();
    }

    public void render(float deltaTime) {
        //if screen is full screen change the size of the frame buffer
        if (frameBuffer.getWidth() != Gdx.graphics.getWidth() || frameBuffer.getHeight() != Gdx.graphics.getHeight()) {
            frameBuffer.dispose();
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            //update the camera viewport
            camera.setViewportWidth(Gdx.graphics.getWidth());
            camera.setViewportHeight(Gdx.graphics.getHeight());
            camera.update();
        }

        editorTopBarMenu.show();
        topMenuBarHeight = editorTopBarMenu.getTopMenuHeight();

        workSpace(deltaTime);
    }

    public void workSpace(float deltaTime) {


        int docker_space_id = ImGui.getID("WorkSpace");


        ImGui.dockBuilderAddNode(docker_space_id);
        //position and size of the docker space
        ImGui.dockBuilderSetNodePos(docker_space_id, new ImVec2(0, topMenuBarHeight));
        ImGui.dockBuilderSetNodeSize(docker_space_id, new ImVec2(viewport.getSizeX(), viewport.getSizeY() - topMenuBarHeight));
//
        imgui.type.ImInt topDockerId = new ImInt(0); //same place 0 and 0
        imgui.type.ImInt bottomDockerId = new ImInt(0);
        ImGui.dockBuilderSplitNode(docker_space_id, ImGuiDir.Up, 0.7f, topDockerId, bottomDockerId);


//
        ImGuiDockNode topNode = ImGui.dockBuilderGetNode(topDockerId.get());
        ImGuiDockNode bottomNode = ImGui.dockBuilderGetNode(bottomDockerId.get());

        ImGui.dockBuilderDockWindow("scene_view", topNode.getID());
        ImGui.dockBuilderDockWindow("assets_view", bottomNode.getID());

        ImGui.dockBuilderFinish(docker_space_id);


        //work space
        //let focus paa too child windows
        ImGui.begin("editor_view", CUSTOM_WINDOW_FLAGS);
        ImGui.setWindowSize(viewport.getSizeX(), viewport.getSizeY() - topMenuBarHeight);
        ImGui.setWindowPos(0, topMenuBarHeight);
        ImGui.dockSpace(docker_space_id, new ImVec2(0, 0), ImGuiDockNodeFlags.AutoHideTabBar | ImGuiDockNodeFlags.PassthruCentralNode);

        //Scene View
        ImGui.begin("scene_view", CUSTOM_DOCK_NODE_FLAGS);
        sceneViewer(deltaTime, ImGui.getWindowSize());
        ImGui.end();


        //Asset Viewer
        ImGui.begin("assets_view", CUSTOM_DOCK_NODE_FLAGS);
        editorAssetViewer.show();
        ImGui.end();
        ImGui.end();
    }

    private ImVec2 mousePositionOnSceneScrene;

    private void sceneViewer(float deltaTime, ImVec2 screen_size) {

        ImGui.beginChild("SceneView");

        if ((ImGui.isWindowHovered())) {
            if (mousePositionOnSceneScrene != null && ImGui.getIO().getMousePos() != mousePositionOnSceneScrene) {
                ImGui.getIO().setMousePos(mousePositionOnSceneScrene.x, mousePositionOnSceneScrene.y);
            }

            ImGui.getIO().setWantCaptureMouse(false);
            ImGui.getIO().setWantCaptureKeyboard(false);
            ImGui.getIO().setWantSetMousePos(false);
            mousePositionOnSceneScrene = new ImVec2(ImGui.getMousePos().x, ImGui.getMousePos().y);
        }


        renderSceneManagerToFrameBuffer(deltaTime, screen_size);
        ImGui.image(frameBuffer.getColorBufferTexture().getTextureObjectHandle(), new ImVec2(screen_size.x, screen_size.y - topMenuBarHeight), new ImVec2(0, 1), new ImVec2(1, 0));
        ImGui.endChild();

    }

    private void renderSceneManagerToFrameBuffer(float deltaTime, ImVec2 screen_size) {
        frameBuffer.begin();
        if (sceneInstance != null) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            sceneInstance.render(deltaTime);
        }
        frameBuffer.end();
    }

    public void dispose() {
        frameBuffer.dispose();
    }

}

