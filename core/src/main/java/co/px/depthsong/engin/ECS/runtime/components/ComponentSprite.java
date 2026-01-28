package co.px.depthsong.engin.ECS.runtime.components;

import co.px.depthsong.engin.ECS.core.abstractClasses.EcsComponent;
import co.px.depthsong.engin.enginUtils.GameSprites;
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

    private String spriteName;
    private Sprite sprite;
    private Vector3 position;

    public ComponentSprite() {
        this.position = new Vector3(0,0,0);

        FileHandle textureFile = Gdx.files.internal("images/untitled.png");
        this.sprite = new Sprite(new Texture(textureFile));
        this.sprite.setPosition(this.position.x, this.position.y);
    }

    public ComponentSprite(String spriteName) {
        this.position = new Vector3(0,0,0);
        this.spriteName = spriteName;
        this.sprite = GameSprites.getInstance().getSprite(this.spriteName);
        this.sprite.setPosition(position.x, position.y);
    }

    public ComponentSprite(String spriteName, Vector3 position) {
        this.sprite = GameSprites.getInstance().getSprite(spriteName);
        this.sprite.setPosition(position.x, position.y);
    }

    public void setSprite(String spriteName){
        this.spriteName = spriteName;
        this.sprite = GameSprites.getInstance().getSprite(spriteName);
    }

}
