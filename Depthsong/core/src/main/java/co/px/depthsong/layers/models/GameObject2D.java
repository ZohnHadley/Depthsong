package co.px.depthsong.layers.models;

import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.components.ComponentCubeCollider;
import co.px.depthsong.ECS.components.ComponentSprite;
import co.px.depthsong.gameUtils.GeneralUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GameObject2D extends EcsEntity {
    @Expose
    protected ComponentSprite componentSprite;
    @Expose(serialize = false, deserialize = false)
    protected ComponentCubeCollider componentCubeCollider;

    protected final ShapeRenderer SHAPERENDERER = new ShapeRenderer();
    public GameObject2D() {
        super();
        this.componentCubeCollider = new ComponentCubeCollider();

        this.componentSprite = new ComponentSprite();

        this.componentSprite.setPosition(this.getComponentTransform().getPosition().x,  this.getComponentTransform().getPosition().y);
        this.getComponentList().add(componentSprite);
    }

    public Vector2 getPosition(){
        return GeneralUtils.vector3ToVector2(this.getComponentTransform().getCenter());
    }

    public void setPosition(float x, float y){
        this.getComponentTransform().setPosition(new Vector3(x, y, 0));
    }

    public void update(float delta){
     }

    public void draw(SpriteBatch batch){
        this.componentCubeCollider.setDimensions((int) this.componentSprite.getSprite().getWidth(), (int) this.componentSprite.getSprite().getHeight());
        this.componentCubeCollider.setPosition(getPosition().x, getPosition().y);
        this.componentSprite.setPosition(getPosition().x, getPosition().y);
        this.componentSprite.getSprite().draw(batch);
    }
}
