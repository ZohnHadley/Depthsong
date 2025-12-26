package co.px.depthsong.engineCore.models;

import co.px.depthsong.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.components.runtime.ComponentBoxCollider;
import co.px.depthsong.ECS.components.runtime.ComponentSprite;
import co.px.depthsong.ECS.components.runtime.ComponentTransform;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameObject2D extends EcsEntity {
    protected ComponentSprite componentSprite;
    protected ComponentBoxCollider componentBoxCollider;
    private ComponentTransform componentTransform;

    protected final ShapeRenderer SHAPERENDERER = new ShapeRenderer();

    public GameObject2D() {
        super();
        this.componentTransform = new ComponentTransform();

        this.componentSprite = new ComponentSprite();
        this.componentSprite.getPosition().set(this.getComponentTransform().getPosition().x,  this.getComponentTransform().getPosition().y,0);

        this.componentBoxCollider = new ComponentBoxCollider();
        this.componentBoxCollider.setPosition(new Vector2(this.getComponentTransform().getPosition().x,this.getComponentTransform().getPosition().y));

        this.getComponentList().add(componentTransform);
        this.getComponentList().add(componentSprite);
        this.getComponentList().add(componentBoxCollider);
    }


    public void update(float delta){

    }

    public void draw(SpriteBatch batch){

        this.componentTransform.setSize(new Vector3(this.componentSprite.getSprite().getWidth(), this.componentSprite.getSprite().getHeight(), 0));

        this.getComponentBoxCollider().setSize(new Vector2(this.componentSprite.getSprite().getWidth(), this.componentSprite.getSprite().getHeight()));
        this.getComponentBoxCollider().setPosition(new Vector2(this.getComponentTransform().getPosition().x, this.getComponentTransform().getPosition().y));

        this.componentSprite.getSprite().setPosition(this.getComponentTransform().getPosition().x, this.getComponentTransform().getPosition().y);
        this.componentSprite.getSprite().draw(batch);
    }
}
