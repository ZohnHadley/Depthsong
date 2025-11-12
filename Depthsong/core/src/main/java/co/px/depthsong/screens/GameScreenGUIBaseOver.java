package co.px.depthsong.screens;

import co.px.depthsong.layers.models.util.VirtualMouse;
import co.px.depthsong.layers.models.GUIBaseScreen;
import co.px.depthsong.layers.models.entities.ClientPlayer;
import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.layers.managers.ScreenManager;
import co.px.depthsong.gameUtils.GeneralTimer;
import co.px.depthsong.gameUtils.StructGameScreens;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameScreenGUIBaseOver extends GUIBaseScreen {

    private final GameManager gameManager;
    private final ScreenManager screenManager;

    private Label label_screenTitle;
    private Button button_disconnect;
    private Button button_respawn;

    public GameScreenGUIBaseOver(String _title) {
        super(_title);
        gameManager = GameManager.getInstance();
        screenManager = ScreenManager.getInstance();
    }

    private void prepareComponents() {
        label_screenTitle = new Label("Game Over", GUIBaseScreen.skin);
        label_screenTitle.setColor(1, 1, 1, 1);

        button_disconnect = new TextButton("Disconnect", GUIBaseScreen.skin);
        button_disconnect.setColor(Color.RED);
        button_disconnect.pad(10);
        button_disconnect.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (gameManager.isNetworked()) {
                    gameManager.getNetworkManager().disconnect();
                    gameManager.setNetworked(false);
                }

                gameManager.getEntityContext().clearContext();
                gameManager.setInGame(false);

                screenManager.setCurrentScreen(StructGameScreens.mainMenu);
                return true;
            }
        });

        button_respawn = new TextButton("Respawn", GUIBaseScreen.skin);
        button_respawn.setColor(Color.GOLD);
        button_respawn.pad(10);
        button_respawn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onRespawnButtonClicked();
            }
        });

    }

    @Override
    public void show() {
        VirtualMouse.getInstance().setIsInUi(false);
        GameManager.getInstance().setInGame(true);
        prepareComponents();

        Stack panel_stack = new Stack();
        panel_stack.setFillParent(true);

        Image background_panel = new Image(GUIBaseScreen.skin.getDrawable("white"));
        background_panel.setColor(0, 0, 0, 0.5f);
        panel_stack.add(background_panel);

        //

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();

        verticalGroup.addActor(label_screenTitle);

        //add empty
        verticalGroup.addActor(new Label("", GUIBaseScreen.skin));
        //

        verticalGroup.addActor(button_disconnect);
        verticalGroup.addActor(button_respawn);

        panel_stack.add(verticalGroup);
        listenForMouseOver(panel_stack);
        getTable().addActor(panel_stack);
    }

    private GeneralTimer counter = null;

    private void onRespawnButtonClicked() {
        counter = new GeneralTimer();

    }

    @Override
    public void update() {
        super.update();
        if (counter != null) {
            if (counter.secondsHasPassed(0.25f)) {
                ((ClientPlayer)GameManager.getInstance().getEntityContext().getPlayer()).respawn();
                screenManager.setCurrentScreen(StructGameScreens.inGameScreen);
                counter = null;
            }
        }
    }
}
