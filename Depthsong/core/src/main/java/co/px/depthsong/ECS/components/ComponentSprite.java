package co.px.depthsong.ECS.components;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.annotations.Expose;
import lombok.*;

@Builder
@AllArgsConstructor
public class ComponentSprite extends EcsComponent {

    @Getter
    @Setter
    @Expose
    private Texture texture;

    @Expose
    private float positionX, positionY;

    @Getter
    @Expose
    private float width;
    @Getter
    @Expose
    private float height;

    public ComponentSprite() {
        FileHandle textureFile = Gdx.files.internal("images/untitled.png");
        Texture texture = new Texture(textureFile);

    }

    public void setPosition(float x, float y)
    {
        positionX = x;
        positionY = y;
    }

    public Vector2 getPosition()
    {
        return new Vector2(positionX, positionY);
    }

}
