package co.px.depthsong.layers.models;

import co.px.depthsong.layers.models.interfaces.GUIScreen;
import co.px.depthsong.layers.models.util.VirtualMouse;
import co.px.depthsong.layers.managers.GameManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import lombok.Getter;


@Getter
public class GUIBaseScreen implements GUIScreen {
    private final Stage stage;
    private final Table table;
    private final String title;

    public GUIBaseScreen(String _title) {
        this.title = _title;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(this.stage); // Make the stage consume events

        this.table = new Table(skin);
        this.table.setFillParent(true);

        this.stage.addActor(this.table);
    }

    public GUIBaseScreen(String _title, Stage _stage) {
        this.title = _title;
        this.stage = _stage;
        Gdx.input.setInputProcessor(this.stage); // Make the stage consume events

        this.table = new Table(skin);
        this.table.setFillParent(true);

        this.stage.addActor(this.table);
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

    public void show() {}

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    public void resize(int x, int y, int width, int height) {
        stage.getViewport().setScreenBounds(x, y, width, height);
    }

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime());
    }



    public void render() {
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
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
