package co.px.depthsong.screens.local_game_screens;

import co.px.depthsong.layers.models.util.VirtualMouse;
import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.layers.managers.ScreenManager;
import co.px.depthsong.layers.models.GUIBaseScreen;
import co.px.depthsong.gameUtils.StructGameScreens;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GameScreenLocalGUIBaseMenu extends GUIBaseScreen {

    private final ScreenManager screenManager;

    private Label label_screenTitle;
    private Button button_host_game;
    private Button button_join_game;
    private Button button_back;

    public GameScreenLocalGUIBaseMenu(String _title) {
        super(_title, new Stage());
        screenManager = ScreenManager.getInstance();
    }

    private void prepareComponents(){
        label_screenTitle = new Label("LocalGameMenu", GUIBaseScreen.skin);
        label_screenTitle.setFontScale(2);

        button_host_game = new TextButton("Host Game", GUIBaseScreen.skin);
        button_host_game.setColor(Color.GOLD);
        button_host_game.pad(10);

        button_host_game.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                OnHostGame();
                return true;
            }
        });

        button_join_game = new TextButton("Join Game", GUIBaseScreen.skin);
        button_join_game.setColor(Color.GOLD);
        button_join_game.pad(10);

        button_join_game.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                OnJoinGame();
                return true;
            }
        });

        button_back = new TextButton("Back", GUIBaseScreen.skin);
        button_back.setColor(Color.RED);
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
        background_image.setColor(new Color(0f, 0f, 0f, 1f));
        panel_stack.add(background_image);

        //

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();

        verticalGroup.addActor(label_screenTitle);

        //add empty
        verticalGroup.addActor(new Label("", GUIBaseScreen.skin));
        //

        verticalGroup.addActor(button_host_game);
        verticalGroup.addActor(button_join_game);
        verticalGroup.addActor(button_back);

        panel_stack.add(verticalGroup);

        listenForMouseOver(panel_stack);
        getTable().add(panel_stack).expand().fill();
    }

    private void OnHostGame() {
        screenManager.setCurrentScreen(StructGameScreens.hostLocalGameMenu);
    }

    private void OnJoinGame() {
        screenManager.setCurrentScreen(StructGameScreens.joinLocalGameMenu);
    }
}
