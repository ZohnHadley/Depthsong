package co.px.depthsong.engin.ECS.runtime.components;

import co.px.depthsong.engin.ECS.core.abstractClasses.EcsComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class ComponentSprite extends EcsComponent {

    private Sprite sprite;
    private Vector3 position;

    public ComponentSprite() {
        position = new Vector3(0,0,0);

        FileHandle textureFile = Gdx.files.internal("images/untitled.png");
        sprite = new Sprite(new Texture(textureFile));
        sprite.setPosition(position.x, position.y);
    }

}
