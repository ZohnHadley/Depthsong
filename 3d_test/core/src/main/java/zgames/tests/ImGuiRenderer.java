package zgames.tests;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiBackendFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

public class ImGuiRenderer {

    public static ImGuiImplGlfw imGuiGlfw;
    public static ImGuiImplGl3 imGuiGl3;

    public static void initImGui() {
        imGuiGlfw = new ImGuiImplGlfw();
        imGuiGl3 = new ImGuiImplGl3();
        long windowHandle = ((Lwjgl3Graphics) Gdx.graphics).getWindow().getWindowHandle();
        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        io.getFonts().addFontDefault();
        io.getFonts().build();
        io.setConfigFlags(ImGuiConfigFlags.DockingEnable);


        imGuiGlfw.init(windowHandle, true);
        imGuiGl3.init("#version 150");


    }


    private static InputProcessor tmpProcessor;

    public static void startImGui() {
        if (tmpProcessor != null) { // Restore the input processor after ImGui caught all inputs, see #end()
            Gdx.input.setInputProcessor(tmpProcessor);
            tmpProcessor = null;
        }

        imGuiGl3.newFrame();
        imGuiGlfw.newFrame();
        ImGui.newFrame();
    }


    public static void endImGui() {

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        // If ImGui wants to capture the input, disable libGDX's input processor
        if (ImGui.getIO().getWantCaptureKeyboard() || ImGui.getIO().getWantCaptureMouse()) {
            tmpProcessor = Gdx.input.getInputProcessor();
            Gdx.input.setInputProcessor(null);
        }
    }

    public static void disposeImGui() {
        imGuiGl3.shutdown();
        imGuiGl3 = null;
        imGuiGlfw.shutdown();
        imGuiGlfw = null;
        ImGui.destroyContext();
    }


}
