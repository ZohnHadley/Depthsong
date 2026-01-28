package co.px.depthsong.screens.local_game_screens;

import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.engin.engineCore.util.VirtualMouse;
import co.px.depthsong.engin.engineCore.engine_managers.GameManager;
import co.px.depthsong.engin.engineCore.engine_managers.NetworkManager;
import co.px.depthsong.engin.engineCore.engine_managers.ScreenManager;
import co.px.depthsong.engin.network.Local.ClientServer;
import co.px.depthsong.engin.engineCore.model.GUIScreen;
import co.px.depthsong.engin.enginUtils.GameScreensList;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GameScreenJoinLocalGUI extends GUIScreen {
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




    public GameScreenJoinLocalGUI(String _title) {
        super(_title, new Stage());
        ipAddress = "localhost";
        port = "8080";

        label_ipAddress_error = new Label("Invalid IP address", this.getSkin());
        label_ipAddress_error.setColor(Color.RED);

        label_port_error = new Label("Invalid port", this.getSkin());
        label_port_error.setColor(Color.RED);

        label_connection_error = new Label("Connection error", this.getSkin());
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
        label_screenTitle = new Label("Joining Local Game", this.getSkin());
        label_screenTitle.setFontScale(2);

        label_ipAddress = new Label("IP Address", this.getSkin());
        textField_ipAddress = new TextField(ipAddress, this.getSkin());

        label_port = new Label("Port", this.getSkin());
        textField_port = new TextField(port, this.getSkin());
        textField_port.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        textField_port.setMaxLength(5);

        button_join_game = new TextButton("join game", this.getSkin());
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
                    networkManager.setCurrentConnectedState(EnumNetworkClientConnectionStates.DISCONNECTED);
                    printLogError(e.getMessage());
                }
                return true;
            }
        });

        button_back = new TextButton("Back", this.getSkin());
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
        Image background_image = new Image(this.getSkin().getDrawable("white"));
        background_image.setColor(new Color(0f, 0f, 0f, 1f));
        panel_stack.add(background_image);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();


        verticalGroup.addActor(label_screenTitle);
        //add empty
        verticalGroup.addActor(new Label("", this.getSkin()));
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

        if (networkManager.getClientServer() != null && networkManager.getClientServer().isRunning() && networkManager.getConnectionState() == EnumNetworkClientConnectionStates.CONNECTED) {

            connectionError = false;
            screenManager.setCurrentScreen(GameScreensList.characterCreator);

        }

        if (networkManager.getClientServer() != null
            && !networkManager.getClientServer().isRunning()
            && (networkManager.getConnectionState() == EnumNetworkClientConnectionStates.DISCONNECTED)) {
            connectionError = true;
            label_connection_error.setColor(1, 0, 0, 1);
        }


    }
}
