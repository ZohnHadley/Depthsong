package co.px.depthsong.screens.local_game_screens;
//import Thread
import java.lang.Thread;
import co.px.depthsong.layers.models.util.VirtualMouse;
import co.px.depthsong.layers.managers.GameManager;
import co.px.depthsong.layers.managers.NetworkManager;
import co.px.depthsong.layers.managers.ScreenManager;
import co.px.depthsong.network.Local.ClientServer;
import co.px.depthsong.network.Local.HostServer;
import co.px.depthsong.network.NetworkMachine;
import co.px.depthsong.layers.models.GUIBaseScreen;
import co.px.depthsong.gameUtils.StructGameScreens;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class GameScreenHostLocalGUIBase extends GUIBaseScreen {
    private final GameManager gameManager ;
    private final ScreenManager screenManager;
    private final NetworkManager networkManager;

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



    public GameScreenHostLocalGUIBase(String _title) {
        super(_title, new Stage());
        valueIpAddress = "localhost";
        valuePort = "8080";


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
        portValid = valuePort.matches("^[0-9]{1,5}$") && (valuePort.length() >= 4);
    }

    private void prepareComponents() {
        label_screenTitle = new Label("Host Local Game", GUIBaseScreen.skin);
        label_screenTitle.setFontScale(2);

        label_inputError_port = new Label("Invalid port", GUIBaseScreen.skin);
        label_input_port = new Label("Port", GUIBaseScreen.skin);

        textField_port = new TextField(valuePort, GUIBaseScreen.skin);
        textField_port = new TextField(valuePort, GUIBaseScreen.skin);
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

        button_launch_game = new TextButton("launch game", GUIBaseScreen.skin);
        button_launch_game.setColor(Color.GREEN);
        button_launch_game.pad(10);
        button_launch_game.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                OnSubmitLocalGameConnectionInformation();
                return true;
            }
        });

        button_back = new TextButton("Back", GUIBaseScreen.skin);
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
        Image background_image = new Image(GUIBaseScreen.skin.getDrawable("white"));
        background_image.setColor(new Color(0f, 0f, 0f, 1f));
        stack.add(background_image);

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.space(10);
        verticalGroup.center();
        verticalGroup.addActor(label_screenTitle);
        //add empty
        verticalGroup.addActor(new Label("", GUIBaseScreen.skin));
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
            networkManager.setHostServer(new HostServer(Integer.parseInt(valuePort)));
            networkManager.setHostServerThread(Thread.startVirtualThread(networkManager.getHostServer()));

        } catch (Exception e) {
            networkManager.setHostServer(null);
            networkManager.setCurrentConnectedState(NetworkManager.connection_states.OFFLINE);
            printLogError(e.getMessage());
        }


    }
    public void update() {
        super.update();
        //isPortValid(port);
        valuePort = textField_port.getText();

        //wait for server to start by chekcing if the server is running
        if (!gameManager.isNetworked() && networkManager.getHostServer() != null && networkManager.getHostServer().isRunning()) {
            printLog("connecting to local server");
            gameManager.setNetworked(true);

            //launch client server
            clientServer = new ClientServer(valueIpAddress, Integer.parseInt(valuePort));
            networkManager.setClientServer(clientServer);

            networkManager.setClientServerThread(Thread.startVirtualThread(networkManager.getClientServer()));
        }

        if (networkManager.getClientServer() != null && networkManager.getClientServer().isRunning()) {
            printLog("connected to local server");
            screenManager.setCurrentScreen(StructGameScreens.characterCreator);
        }
    }
}
