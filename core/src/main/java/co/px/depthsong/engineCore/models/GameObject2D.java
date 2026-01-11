package co.px.depthsong.engineCore.models;

import co.px.depthsong.ECS.DTO.DTOComponentBoxCollider;
import co.px.depthsong.ECS.DTO.DTOComponentTransform;
import co.px.depthsong.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.core.interfaces.BaseScript;
import co.px.depthsong.ECS.runtime.components.ComponentBoxCollider;
import co.px.depthsong.ECS.runtime.components.ComponentSprite;
import co.px.depthsong.ECS.runtime.components.ComponentTransform;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameObject2D extends EcsEntity implements BaseScript {
    @JsonIgnore
    protected ComponentSprite componentSprite;
    @JsonIgnore
    protected ComponentBoxCollider componentBoxCollider;
    @JsonIgnore
    protected ComponentTransform componentTransform;

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

    @JsonProperty("componentTransform")
    public DTOComponentTransform getDTOComponentTransform(){
        return DTOComponentTransform.toDTO(componentTransform);
    }

    @JsonProperty("componentBoxCollider")
    public DTOComponentBoxCollider getDTOComponentBoxCollider(){
        return DTOComponentBoxCollider.toDTO(componentBoxCollider);
    }

    @Override
    public void Start() {

    }

    @Override
    public void Update(float deltaTime) {

    }
}
