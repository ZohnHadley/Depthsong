package co.px.depthsong.layers.models.interfaces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public interface GUIScreen {
    Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    Stage stage =  new Stage();
    Table table =  new Table();
    String title = "Untitled";
}
