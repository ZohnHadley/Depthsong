package co.px.depthsong.screens.inGame_screens;

import co.px.depthsong.layers.models.util.VirtualMouse;
import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.layers.models.entities.ClientPlayer;
import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.layers.managers.ScreenManager;
import co.px.depthsong.layers.models.GUIBaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GameScreenInGUIBase extends GUIBaseScreen {

    private final GameManager gameManager;
    private final ScreenManager screenManager;
    {
        gameManager = GameManager.getInstance();
        screenManager = ScreenManager.getInstance();
    }

    private ProgressBar playerHealthBar;
    private Label playerHealthLabel;
    private ClientPlayer clientPlayer;

    public GameScreenInGUIBase(String _title) {
        super(_title, new Stage());
    }

    @Override
    public void show() {
        //get player //if removed this wont work
        clientPlayer = (ClientPlayer) gameManager.getEntityContext().getPlayer();
        //at the start of the game the mouse is not in the ui
        VirtualMouse.getInstance().setIsInUi(false);
        GameManager.getInstance().setInGame(true);
        //


        getTable().add(topUI_group()).top().expand().fill();
        getTable().row();
        getTable().add(bottomUI_group()).bottom().expand().fill();


    }

    private Actor topUI_group() {
        Stack panel_stack = new Stack();
        panel_stack.setFillParent(true);

        Image background_panel = new Image(GUIBaseScreen.skin.getDrawable("white"));
        background_panel.setColor(0, 0, 0, 1);
        background_panel.setFillParent(true);
        panel_stack.add(background_panel);
        //where to add etra ui elements
        HorizontalGroup top_ui_group = new HorizontalGroup();
        top_ui_group.space(16);
        top_ui_group.pad(10);
        top_ui_group.setWidth(500);
        top_ui_group.addActor(new Label("Player in game screen", GUIBaseScreen.skin));
        top_ui_group.addActor(new Label("TurnTimer", GUIBaseScreen.skin));
        panel_stack.add(top_ui_group);
        listenForMouseOver(panel_stack);
        //////////////////////////
        Container<Stack> panel_container = new Container<>(panel_stack);
        panel_container.height(42);
        panel_container.minWidth(getStage().getWidth());
        panel_container.setDebug(true);
        return panel_container;
    }


    private Actor bottomUI_group() {
        Stack panel_stack = new Stack();
        panel_stack.setFillParent(true);

        Image background_panel = new Image(GUIBaseScreen.skin.getDrawable("white"));
        background_panel.setColor(0, 0, 0, 1);
        background_panel.setFillParent(true);
        panel_stack.add(background_panel);
        //where to add etra ui elements
        HorizontalGroup top_ui_group = new HorizontalGroup();
        top_ui_group.space(10);
        top_ui_group.pad(10);
        top_ui_group.setWidth(500);
        top_ui_group.addActor(healthBarUI());
        panel_stack.add(top_ui_group);
        listenForMouseOver(panel_stack);
        //////////////////////////
        Container<Stack> panel_container = new Container<>(panel_stack);
        panel_container.height(42);
        panel_container.minWidth(getStage().getWidth());
        panel_container.setDebug(true);
        return panel_container;
    }

    private Actor healthBarUI() {
        Stack health_bar_stack = new Stack();
        health_bar_stack.setFillParent(true);

        int playerHealth = clientPlayer.getHealthPoints();
        playerHealthBar = new ProgressBar(0, playerHealth, 1, false, GUIBaseScreen.skin);
        playerHealthBar.setValue(playerHealth);
        playerHealthBar.setAnimateDuration(0.15f);
        health_bar_stack.add(playerHealthBar);

        playerHealthLabel = new Label("HP:" + playerHealth, GUIBaseScreen.skin);
        health_bar_stack.add(playerHealthLabel);

        Container<Stack> health_bar_container = new Container<>(health_bar_stack);
        health_bar_container.setDebug(true);
        health_bar_container.minWidth(200);
        health_bar_container.maxWidth(300);
        return health_bar_container;
    }

    public void update() {
        if (clientPlayer == null) {
            Gdx.app.log("GameScreenPlayerInGameScreen", "Player is null");
            clientPlayer = (ClientPlayer) EntityContext.getInstance().getPlayer();
            return;
        }
        playerHealthLabel.setText("HP:" + clientPlayer.getHealthPoints());
        playerHealthBar.setValue(clientPlayer.getHealthPoints());

    }


}
