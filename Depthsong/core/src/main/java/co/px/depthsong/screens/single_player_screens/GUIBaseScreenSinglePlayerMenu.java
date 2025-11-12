package co.px.depthsong.screens.single_player_screens;

import co.px.depthsong.layers.models.util.VirtualMouse;
import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.layers.managers.ScreenManager;
import co.px.depthsong.layers.models.GUIBaseScreen;
import co.px.depthsong.gameUtils.StructGameScreens;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GUIBaseScreenSinglePlayerMenu extends GUIBaseScreen {

    private final ScreenManager screenManager;

    private Label label_screenTitle;
    private Button button_newGame;
    private Button button_loadGame;
    private Button button_back;

    public GUIBaseScreenSinglePlayerMenu(String _title) {
        super(_title);
        screenManager = ScreenManager.getInstance();
    }

    private void prepareComponents() {
        label_screenTitle = new Label("Single Player", GUIBaseScreen.skin);
        label_screenTitle.setFontScale(2);


        button_newGame = new TextButton("New Game", GUIBaseScreen.skin);
        button_newGame.pad(10);
        button_newGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setCurrentScreen(StructGameScreens.characterCreator);
                return true;
            }
        });

        button_loadGame = new TextButton("Load Game", GUIBaseScreen.skin);
        button_loadGame.pad(10);
        button_loadGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //TODO: implement load game (functionality and menu)
                //screenManager.setCurrentScreen(Global_Screens.loadGameMenu);
                return true;
            }
        });

        button_back = new TextButton("Back", GUIBaseScreen.skin);
        button_back.pad(10);
        button_back.addListener(new InputListener() {
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
        //add empty
        verticalGroup.addActor(new Label("", GUIBaseScreen.skin));
        //
        verticalGroup.addActor(button_newGame);
        verticalGroup.addActor(button_loadGame);
        verticalGroup.addActor(button_back);

        panel_stack.add(verticalGroup);
        listenForMouseOver(panel_stack);
        getTable().add(panel_stack).expand().fill();
    }

}
