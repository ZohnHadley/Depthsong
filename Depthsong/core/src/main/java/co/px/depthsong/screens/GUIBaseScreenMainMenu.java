package co.px.depthsong.screens;

import co.px.depthsong.layers.models.GUIBaseScreen;
import co.px.depthsong.layers.managers.ScreenManager;
import co.px.depthsong.gameUtils.StructGameScreens;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GUIBaseScreenMainMenu extends GUIBaseScreen {


    private final ScreenManager screenManager;
    private Label label_screenTitle;
    private Button button_offlineGame;
    private Button button_localGame;
    private Button button_settings;

    public GUIBaseScreenMainMenu(String _title) {
        super(_title, new Stage());
        screenManager = ScreenManager.getInstance();
    }

    private void prepareComponents(){

        label_screenTitle = new Label("Crypts&Runes", GUIBaseScreen.skin);
        label_screenTitle.setFontScale(2);


        button_offlineGame = new TextButton("single player", GUIBaseScreen.skin);
        button_offlineGame.pad(10);
        button_offlineGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setCurrentScreen(StructGameScreens.playOfflineMenu);
                return true;
            }
        });

        button_localGame = new TextButton("local game", GUIBaseScreen.skin);
        button_localGame.pad(10);
        button_localGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setCurrentScreen(StructGameScreens.localGameMenu);
                return true;
            }
        });

        //TODO add a settings screen
        button_settings = new TextButton("settings", GUIBaseScreen.skin);
        button_settings.pad(10);

    }

    @Override
    public void show() {
//        VirtualMouse.getInstance().setIsInUi(true);
//        GameManager.getInstance().setInGame(false);
        prepareComponents();

        Stack panel_stack = new Stack();
        Image background_image = new Image(GUIBaseScreen.skin.getDrawable("white"));
        background_image.setColor(new Color(0f, 0f, 0f, 1f));
        panel_stack.add(background_image);


        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();


        verticalGroup.addActor(label_screenTitle);
        //space
        verticalGroup.addActor(new Label("", GUIBaseScreen.skin));
        //
        verticalGroup.addActor(button_offlineGame);
        verticalGroup.addActor(button_localGame);
        verticalGroup.addActor(button_settings);

        panel_stack.add(verticalGroup);
        listenForMouseOver(panel_stack);
        getTable().add(panel_stack).expand().fill();
    }
}
