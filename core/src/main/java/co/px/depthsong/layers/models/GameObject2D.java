package co.px.depthsong.layers.models;

import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.components.ComponentCubeCollider;
import co.px.depthsong.ECS.components.ComponentSprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameObject2D extends EcsEntity {
    protected ComponentSprite componentSprite;
    protected ComponentCubeCollider componentCubeCollider;

    protected final ShapeRenderer SHAPERENDERER = new ShapeRenderer();

    public GameObject2D() {
        super();
        this.componentCubeCollider = new ComponentCubeCollider();
        this.componentCubeCollider.setPosition(this.getComponentTransform().getPosition().x, this.getComponentTransform().getPosition().y);

        this.componentSprite = new ComponentSprite();
        this.componentSprite.setPosition(this.getComponentTransform().getPosition().x,  this.getComponentTransform().getPosition().y);
        this.getComponentList().add(componentSprite);
    }


    public void update(float delta){

    }

    public void draw(SpriteBatch batch){

        this.getComponentTransform().setSize(new Vector3(this.componentSprite.getSprite().getWidth(), this.componentSprite.getSprite().getHeight(), 0));
        this.getComponentCubeCollider().setSize(new Vector3(this.componentSprite.getSprite().getWidth(), this.componentSprite.getSprite().getHeight(), 0));

        this.componentSprite.setPosition(this.getComponentTransform().getPosition().x, this.getComponentTransform().getPosition().y);
        this.componentSprite.getSprite().draw(batch);
    }
}
