package game.JFrame_UI.customGameUi.screens;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public abstract class ICustomUiScreen {
    @Getter
    private String title = "untitled_screen";

    public ICustomUiScreen(){}


    public Container init() {
        return null;
    }

    public void update() {
    }

    public void dispose() {

    }


}
