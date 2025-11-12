package co.px.depthsong.layers.models;

import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.components.ComponentCubeCollider;
import co.px.depthsong.ECS.components.ComponentSprite;
import co.px.depthsong.gameUtils.GeneralUtils;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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
    private Sprite sprite;
    public GameObject2D() {
        super();
        this.componentCubeCollider = new ComponentCubeCollider();

        this.componentSprite = new ComponentSprite();

        this.componentSprite.setPosition(this.getComponentTransform().getPosition().x,  this.getComponentTransform().getPosition().y);
        this.getComponentList().add(componentSprite);
        sprite =  new Sprite(componentSprite.getTexture(), 32 , 32);
    }

    public GameObject2D(String name) {
        super(name);
        this.componentCubeCollider = new ComponentCubeCollider();

        this.componentSprite = new ComponentSprite();

        this.componentSprite.setPosition(this.getComponentTransform().getPosition().x,  this.getComponentTransform().getPosition().y);
        this.getComponentList().add(componentSprite);
    }

    public Vector2 getPosition(){
        return GeneralUtils.vector3ToVector2(this.getComponentTransform().getPosition());
    }
    public void update(float delta){
     }

    public void draw(SpriteBatch batch){
        float posX= this.getComponentTransform().getPosition().x - (this.componentSprite.getWidth() * 0.5f);
        float posY = this.getComponentTransform().getPosition().y - (this.componentSprite.getHeight() * 0.5f);
        this.componentCubeCollider.setDimensions((int) this.componentSprite.getWidth(), (int) this.componentSprite.getHeight());
        this.componentCubeCollider.setPosition(posX, posY);

        this.componentSprite.setPosition(posX,  posY);
        this.sprite.draw(batch);
    }
}
