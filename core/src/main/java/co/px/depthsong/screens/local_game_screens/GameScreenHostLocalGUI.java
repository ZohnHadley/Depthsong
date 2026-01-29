package co.px.depthsong.screens.local_game_screens;
//import Thread
import java.lang.Thread;

import co.px.depthsong.engin.engineCore.engine_managers.enums.EnumNetworkClientConnectionStates;
import co.px.depthsong.engin.engineCore.util.VirtualMouse;
import co.px.depthsong.engin.engineCore.engine_managers.GameManager;
import co.px.depthsong.engin.engineCore.engine_managers.NetworkMachineManager;
import co.px.depthsong.engin.engineCore.engine_managers.ScreenManager;
import co.px.depthsong.engin.network.Local.HostServer;
import co.px.depthsong.engin.network.NetworkMachine;
import co.px.depthsong.engin.engineCore.model.GUIScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GameScreenHostLocalGUI extends GUIScreen {
    private final GameManager gameManager ;
    private final ScreenManager screenManager;
    private final NetworkMachineManager networkMachineManager;

    private Label label_screenTitle;
    private Label label_input_port;
    private Label label_inputError_port;
    private TextField textField_port;
    private Button button_launch_game;
    private Button button_back;


    //TODO figure out if i want to add validation with error text
    private NetworkMachine clientServer;
    private String valueIpAddress;
    private String valuePort;


    private boolean ipValid = false;
    private boolean portValid = false;



    public GameScreenHostLocalGUI(String _title) {
        super(_title, new Stage());
        valueIpAddress = "localhost";
        valuePort = "8080";


        gameManager = GameManager.getInstance();
        screenManager = ScreenManager.getInstance();
        networkMachineManager = gameManager.getNetworkMachineManager();
    }

    private void isIpValid(String ip) {
        if (ip.equals("localhost")) {
            ipValid = true;
            return;
        }
        ipValid = ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
    }

    private void isPortValid(String port) {
        portValid = valuePort.matches("^[0-9]{1,5}$") && (valuePort.length() >= 4);
    }

    private void prepareComponents() {
        label_screenTitle = new Label("Host Local Game", this.getSkin());
        label_screenTitle.setFontScale(2);

        label_inputError_port = new Label("Invalid port", this.getSkin());
        label_input_port = new Label("Port", this.getSkin());

        textField_port = new TextField(valuePort, this.getSkin());
        textField_port = new TextField(valuePort, this.getSkin());
        textField_port.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        textField_port.setMaxLength(5);
        textField_port.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                valuePort = textField_port.getText();

                if (keycode == 66) {
                    OnSubmitLocalGameConnectionInformation();
                }
                return true;
            }
        });

        button_launch_game = new TextButton("launch game", this.getSkin());
        button_launch_game.setColor(Color.GREEN);
        button_launch_game.pad(10);
        button_launch_game.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                OnSubmitLocalGameConnectionInformation();
                return true;
            }
        });

        button_back = new TextButton("Back", this.getSkin());
        button_back.setColor(Color.RED);
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

        Stack stack = new Stack();
        Image background_image = new Image(this.getSkin().getDrawable("white"));
        background_image.setColor(new Color(0f, 0f, 0f, 1f));
        stack.add(background_image);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();
        verticalGroup.addActor(label_screenTitle);
        //add empty
        verticalGroup.addActor(new Label("", this.getSkin()));
        //

        label_inputError_port.setColor(1, 1, 1, 0);
        verticalGroup.addActor(label_inputError_port);
        verticalGroup.addActor(label_input_port);
        verticalGroup.addActor(textField_port);
        verticalGroup.addActor(button_launch_game);
        verticalGroup.addActor(button_back);

        stack.add(verticalGroup);
        listenForMouseOver(stack);
        getTable().add(stack).expand().fill();
    }



    private void OnSubmitLocalGameConnectionInformation() {

        //TODO input validation for when joining local server

        try {
            //launch host server
            networkMachineManager.setHostServer(new HostServer(Integer.parseInt(valuePort)));
//            networkMachineManager.setHostServerThread(Thread.startVirtualThread(networkMachineManager));

        } catch (Exception e) {
            networkMachineManager.setHostServer(null);
            networkMachineManager.setCurrentConnectedState(EnumNetworkClientConnectionStates.DISCONNECTED);
            printLogError(e.getMessage());
        }


    }
    public void update() {
        super.update();
        //isPortValid(port);
        valuePort = textField_port.getText();
        //TODO: FIX THIS SHIT
        //wait for server to start by chekcing if the server is running
//        if (gameManager.getNetworkManager().getConnectionState() == NetworkClientConnectionStates.DISCONNECTED
//            && networkManager.getHostServer() != null
//            && networkManager.getHostServer().isRunning()
//        ) {
//            printLog("connecting to local server");
//            gameManager.getNetworkManager().setCurrentConnectedState();
//
//            //launch client server
//            clientServer = new ClientServer(valueIpAddress, Integer.parseInt(valuePort));
//            networkManager.setClientServer(clientServer);
//
//            networkManager.setClientServerThread(Thread.startVirtualThread(networkManager.getClientServer()));
//        }
//
//        if (networkManager.getClientServer() != null && networkManager.getClientServer().isRunning()) {
//            printLog("connected to local server");
//            screenManager.setCurrentScreen(GameScreensList.characterCreator);
//        }
    }
}
