package co.px.depthsong.game.models;

import co.px.depthsong.engin.ECS.DTO.DTOComponentBoxCollider;
import co.px.depthsong.engin.ECS.DTO.DTOComponentSprite;
import co.px.depthsong.engin.ECS.DTO.DTOComponentTransform;
import co.px.depthsong.engin.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.engin.ECS.core.interfaces.BaseScript;
import co.px.depthsong.engin.ECS.runtime.components.ComponentBoxCollider;
import co.px.depthsong.engin.ECS.runtime.components.ComponentSprite;
import co.px.depthsong.engin.ECS.runtime.components.ComponentTransform;
import co.px.depthsong.game.models.entities.ClientPlayer;
import co.px.depthsong.game.models.entities.OtherPlayer;
import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;


@JsonSubTypes({
    @JsonSubTypes.Type(value = ClientPlayer.class, name = "player"),
    @JsonSubTypes.Type(value = OtherPlayer.class, name = "otherPlayer")
    }
)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")

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

    //JSON : serialize ECS Components
    @JsonProperty("componentTransform")
    public DTOComponentTransform getDTOComponentTransform(){
        return DTOComponentTransform.toDTO(componentTransform);
    }

    @JsonProperty("componentSprite")
    public DTOComponentSprite getDTOComponentSprite(){
        return DTOComponentSprite.toDTO(componentSprite);
    }

    @JsonProperty("componentBoxCollider")
    public DTOComponentBoxCollider getDTOComponentBoxCollider(){
        return DTOComponentBoxCollider.toDTO(componentBoxCollider);
    }
    //

    @Override
    public void Start() {

    }

    @Override
    public void Update(float deltaTime) {

    }
}
