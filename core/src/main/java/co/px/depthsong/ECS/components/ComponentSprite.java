package co.px.depthsong.ECS.components;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.annotations.Expose;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class ComponentSprite extends EcsComponent {

    @Getter
    @Setter
    private Sprite sprite;

    private float positionX, positionY;

    public ComponentSprite() {
        FileHandle textureFile = Gdx.files.internal("images/untitled.png");
        sprite = new Sprite(new Texture(textureFile));
    }

    public void setPosition(float x, float y)
    {
        positionX = x + sprite.getWidth() * 0.5f ;
        positionY = y + sprite.getHeight() * 0.5f;
    }

    public Vector2 getPosition()
    {
        return new Vector2(positionX, positionY);
    }

}
