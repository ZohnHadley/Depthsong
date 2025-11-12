package co.px.depthsong.screens.local_game_screens;

import co.px.depthsong.layers.models.util.VirtualMouse;
import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.layers.managers.NetworkManager;
import co.px.depthsong.layers.managers.ScreenManager;
import co.px.depthsong.network.Local.ClientServer;
import co.px.depthsong.layers.models.GUIBaseScreen;
import co.px.depthsong.gameUtils.StructGameScreens;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GameScreenJoinLocalGUIBase extends GUIBaseScreen {
    private final GameManager gameManager ;
    private final ScreenManager screenManager;
    private final NetworkManager networkManager;

    //fireUserEventTriggered

    private String ipAddress;
    private String port;


    private Label label_screenTitle;
    private Label label_ipAddress;
    private TextField textField_ipAddress;
    private Label label_port;
    private TextField textField_port;
    private Button button_join_game;
    private Button button_back;


    private Label label_connection_error;
    private Label label_ipAddress_error;
    private Label label_port_error;
    private boolean connectionError = false;
    private boolean portValid = false;
    private boolean ipValid = false;




    public GameScreenJoinLocalGUIBase(String _title) {
        super(_title, new Stage());
        ipAddress = "localhost";
        port = "8080";

        label_ipAddress_error = new Label("Invalid IP address", GUIBaseScreen.skin);
        label_ipAddress_error.setColor(Color.RED);

        label_port_error = new Label("Invalid port", GUIBaseScreen.skin);
        label_port_error.setColor(Color.RED);

        label_connection_error = new Label("Connection error", GUIBaseScreen.skin);
        label_connection_error.setColor(Color.RED);

        gameManager = GameManager.getInstance();
        screenManager = ScreenManager.getInstance();
        networkManager = gameManager.getNetworkManager();
    }

    private void isIpValid(String ip) {
        if (ip.equals("localhost")) {
            ipValid = true;
            return;
        }
        ipValid = ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
    }

    private void isPortValid(String port) {
        portValid = port.matches("^[0-9]{1,5}$") && (port.length() >= 4);
    }

    private void prepareComponents() {
        label_screenTitle = new Label("Joining Local Game", GUIBaseScreen.skin);
        label_screenTitle.setFontScale(2);

        label_ipAddress = new Label("IP Address", GUIBaseScreen.skin);
        textField_ipAddress = new TextField(ipAddress, GUIBaseScreen.skin);

        label_port = new Label("Port", GUIBaseScreen.skin);
        textField_port = new TextField(port, GUIBaseScreen.skin);
        textField_port.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        textField_port.setMaxLength(5);

        button_join_game = new TextButton("join game", GUIBaseScreen.skin);
        button_join_game.pad(10);
        button_join_game.setColor(Color.GREEN);
        button_join_game.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                try {
                    //launch host local server
                    networkManager.setClientServer(new ClientServer(ipAddress, Integer.parseInt(port)));
                    networkManager.setClientServerThread(Thread.startVirtualThread(networkManager.getClientServer()));

                } catch (Exception e) {
                    networkManager.setClientServer(null);
                    networkManager.setCurrentConnectedState(NetworkManager.connection_states.OFFLINE);
                    printLogError(e.getMessage());
                }
                return true;
            }
        });

        button_back = new TextButton("Back", GUIBaseScreen.skin);
        button_back.pad(10);
        button_back.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setCurrentScreen(screenManager.getPreviousScreen());
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

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();


        verticalGroup.addActor(label_screenTitle);
        //add empty
        verticalGroup.addActor(new Label("", GUIBaseScreen.skin));
        //
        verticalGroup.addActor(label_ipAddress);
        verticalGroup.addActor(textField_ipAddress);
        verticalGroup.addActor(label_port);
        verticalGroup.addActor(textField_port);
        verticalGroup.addActor(button_join_game);
        verticalGroup.addActor(button_back);

        panel_stack.add(verticalGroup);
        listenForMouseOver(panel_stack);
        getTable().add(panel_stack).expand().fill();
    }


    @Override
    public void update() {
        super.update();
        isIpValid(ipAddress);
        isPortValid(port);

        ipAddress = textField_ipAddress.getText();
        port = textField_port.getText();

        if (networkManager.getClientServer() != null && networkManager.getClientServer().isRunning() && networkManager.getConnectionState() == NetworkManager.connection_states.CONNECTED) {

            connectionError = false;
            screenManager.setCurrentScreen(StructGameScreens.characterCreator);

        }

        if (networkManager.getClientServer() != null
            && !networkManager.getClientServer().isRunning()
            && (networkManager.getConnectionState() == NetworkManager.connection_states.OFFLINE)) {
            connectionError = true;
            label_connection_error.setColor(1, 0, 0, 1);
        }


    }
}
