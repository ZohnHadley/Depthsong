package co.px.depthsong.core.models;

import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.components.ComponentCubeCollider;
import co.px.depthsong.ECS.components.ComponentSprite;
import co.px.depthsong.ECS.components.ComponentTransform;
import com.badlogic.gdx.Gdx;
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
    private ComponentTransform componentTransform;

    protected final ShapeRenderer SHAPERENDERER = new ShapeRenderer();

    public GameObject2D() {
        super();
        this.componentTransform = new ComponentTransform();

        this.componentSprite = new ComponentSprite();
        this.componentSprite.getPosition().set(this.getComponentTransform().getPosition().x,  this.getComponentTransform().getPosition().y,0);

        this.componentCubeCollider = new ComponentCubeCollider();
        this.componentCubeCollider.setPosition(this.getComponentTransform().getPosition().x, this.getComponentTransform().getPosition().y);

        this.getComponentList().add(componentTransform);
        this.getComponentList().add(componentSprite);
        this.getComponentList().add(componentCubeCollider);
    }


    public void update(float delta){

    }

    public void draw(SpriteBatch batch){

        this.componentTransform.setSize(new Vector3(this.componentSprite.getSprite().getWidth(), this.componentSprite.getSprite().getHeight(), 0));

        this.getComponentCubeCollider().setSize(new Vector3(this.componentSprite.getSprite().getWidth(), this.componentSprite.getSprite().getHeight(), 0));
        this.getComponentCubeCollider().setPosition(this.getComponentTransform().getPosition().x,this.getComponentTransform().getPosition().y);

        this.componentSprite.getSprite().setPosition(this.getComponentTransform().getPosition().x, this.getComponentTransform().getPosition().y);
        this.componentSprite.getSprite().draw(batch);
    }
}
