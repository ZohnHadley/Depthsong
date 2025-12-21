package co.px.depthsong.ECS.components;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lombok.*;

@AllArgsConstructor

@Getter
@Setter
public class ComponentSprite extends EcsComponent {

    private Sprite sprite;
    private Vector3 position = new Vector3(0,0,0);

    public ComponentSprite() {
        FileHandle textureFile = Gdx.files.internal("images/untitled.png");
        sprite = new Sprite(new Texture(textureFile));
        sprite.setPosition(position.x, position.y);
    }

}
