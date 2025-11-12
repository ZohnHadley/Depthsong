package co.px.depthsong.screens.inGame_screens;

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

public class GUIBaseScreenCharacterCreator extends GUIBaseScreen {

    private final GameManager gameManager;
    private final ScreenManager screenManager;

    private Label label_screenTitle;
    private Label label_userName;
    private TextField textField_userName;
    private Button button_createCharacter;
    private Button button_back;

    public GUIBaseScreenCharacterCreator(String _title) {
        super(_title, new Stage());
        gameManager = GameManager.getInstance();
        screenManager = ScreenManager.getInstance();
    }

    private void prepareComponents() {

        label_screenTitle = new Label("Character Creation", GUIBaseScreen.skin);
        label_screenTitle.setFontScale(2);

        label_userName = new Label("UserName", GUIBaseScreen.skin);
        textField_userName = new TextField("", GUIBaseScreen.skin);

        button_createCharacter = new TextButton("Create Character", GUIBaseScreen.skin);
        button_createCharacter.pad(10);
        button_createCharacter.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                OnSubmit();
                return true;
            }
        });

        button_back = new TextButton("Back", GUIBaseScreen.skin);
        button_back.pad(10);
        button_back.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (gameManager.isNetworked()) {
                    gameManager.getNetworkManager().disconnect();
                    gameManager.setNetworked(false);
                }
                gameManager.setInGame(false);

                screenManager.setCurrentScreen(screenManager.getPreviousScreen());
                return true;
            }
        });
    }

    @Override
    public void show() {
        //at the start of the game the mouse is not in the ui
        VirtualMouse.getInstance().setIsInUi(true);
        GameManager.getInstance().setInGame(false);
        prepareComponents();

        Stack panel_stack = new Stack();
        Image background_image = new Image(GUIBaseScreen.skin.getDrawable("white"));
        background_image.setColor(new Color(0f, 0f, 0f, 1f));
        panel_stack.add(background_image);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();

        verticalGroup.addActor(label_screenTitle);
        //add empty
        verticalGroup.addActor(new Label("", GUIBaseScreen.skin));
        //

        verticalGroup.addActor(label_userName);
        verticalGroup.addActor(textField_userName);
        verticalGroup.addActor(button_createCharacter);
        verticalGroup.addActor(button_back);

        panel_stack.add(verticalGroup);
        listenForMouseOver(panel_stack);
        getTable().add(panel_stack).expand().fill();
    }

    private boolean isCharacterNameValidForGame = false;
    private void OnSubmit() {
        if (textField_userName.getText().isEmpty() || textField_userName.getText().length() < 3) {
            return;
        }
        //TODO solve
//        new Player(textField_userName.getText(), 5, 1);
        isCharacterNameValidForGame = true;
    }

    @Override
    public void update() {
        super.update();

        if (isCharacterNameValidForGame ) {
            printLog("Username is valid character created");
            try {
                gameManager.setInGame(true);
                Thread.sleep(500);
                isCharacterNameValidForGame = false;
                screenManager.setCurrentScreen(StructGameScreens.inGameScreen);

            } catch (InterruptedException e) {
                printLogError("Error in creating character");
            }
        }
    }
}
