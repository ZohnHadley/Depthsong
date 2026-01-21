package co.px.depthsong.engineCore.models;

import co.px.depthsong.engineCore.util.GameCamera;
import co.px.depthsong.engineCore.util.VirtualMouse;
import co.px.depthsong.engineCore.engine_managers.GameManager;
import co.px.depthsong.screens.GUIScreenContext;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GUIScreen{

    private final GUIScreenContext guiScreenContext = GUIScreenContext.getInstance();

    private Long id;
    private final Skin skin;

    private final Stage stage;
    private final Table table;
    private final String title;

    public GUIScreen(String title) {
        this.title = title;
        this.stage = new Stage();

        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.table = new Table(skin);
        this.table.setFillParent(true);

        this.stage.addActor(this.table);
        Gdx.input.setInputProcessor(this.stage); // Make the stage consume events

        guiScreenContext.addScreen(this);
    }

    public GUIScreen(String title, Stage stage) {
        this.title = title;
        this.stage = stage;

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        this.table = new Table(skin);
        this.table.setFillParent(true);

        this.stage.addActor(this.table);
        Gdx.input.setInputProcessor(this.stage); // Make the stage consume events

        guiScreenContext.addScreen(this);
    }

    protected void listenForMouseOver(Group group) {
        group.addCaptureListener(
            new EventListener() {
                @Override
                public boolean handle(Event event) {
                    VirtualMouse.getInstance().setIsInUi(true);
                    GameManager.getInstance().setInGame(false);
                    if(event.toString().equals("exit")) {
                        VirtualMouse.getInstance().setIsInUi(false);
                        GameManager.getInstance().setInGame(true);
                        return false;
                    }
                    return true;
                }
            }
        );


    }


    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    public void resize(int x, int y, int width, int height) {
        stage.getViewport().setScreenBounds(x, y, width, height);
    }

    public void show() {}

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    public void render(GameCamera camera) {
        stage.getViewport().setScreenWidth((int) camera.viewportWidth);
        stage.getViewport().setScreenHeight((int) camera.viewportHeight);
        stage.getViewport().apply();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }

    public void clear() {
        table.clear();
    }

    protected void printLogError(String message) {
        Gdx.app.error("screen(" + this.title + ")", message);
    }

    protected void printLog(String message) {
        Gdx.app.log("screen(" + this.title + ")", message);
    }
}
