package co.px.depthsong.screens.single_player_screens;

import co.px.depthsong.layers.models.util.VirtualMouse;
import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.layers.managers.ScreenManager;
import co.px.depthsong.layers.models.GUIBaseScreen;
import co.px.depthsong.gameUtils.StructGameScreens;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GUIBaseScreenSinglePlayerPauseMenu extends GUIBaseScreen {

    private final ScreenManager screenManager;

    private Label label_screenTitle;
    private Button button_resumeGame;
    private Button button_saveGame;
    private Button button_loadGame;
    private Button button_quitGame;

    public GUIBaseScreenSinglePlayerPauseMenu(String _title) {
        super(_title);
        screenManager = ScreenManager.getInstance();
    }

    private void prepareComponents() {
        label_screenTitle = new Label("Pause Menu", GUIBaseScreen.skin);

        button_resumeGame = new TextButton("Resume Game", GUIBaseScreen.skin);
        button_resumeGame.pad(10);
        button_resumeGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setCurrentScreen(StructGameScreens.inGameScreen);
                return true;
            }
        });

        button_saveGame = new TextButton("Save Game", GUIBaseScreen.skin);
        button_saveGame.pad(10);

        button_loadGame = new TextButton("Load Game", GUIBaseScreen.skin);
        button_loadGame.pad(10);

        button_quitGame = new TextButton("Quit game to main menu", GUIBaseScreen.skin);
        button_quitGame.pad(10);
        button_quitGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setCurrentScreen(StructGameScreens.mainMenu);
                return true;
            }
        });
    }

    @Override
    public void show() {
        VirtualMouse.getInstance().setIsInUi(true);
        GameManager.getInstance().setInGame(false);
        prepareComponents();

        Stack panel_stack = new Stack();
        Image background_image = new Image(GUIBaseScreen.skin.getDrawable("white"));
        background_image.setColor(0, 0, 0, 1);
        panel_stack.add(background_image);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();

        verticalGroup.addActor(label_screenTitle);
        verticalGroup.addActor(button_resumeGame);
        verticalGroup.addActor(button_saveGame);
        verticalGroup.addActor(button_loadGame);

        panel_stack.add(verticalGroup);
        getTable().addActor(panel_stack);
        getTable().add(panel_stack).expand().fill();
    }
}
