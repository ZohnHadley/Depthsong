package co.px.depthsong.engineCore.models.entities;

import co.px.depthsong.enginUtils.GameSprites;
import co.px.depthsong.engineCore.models.GameObject2D;
import com.badlogic.gdx.math.Vector3;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIncludeProperties({ "name", "componentTransform" })
@JsonPropertyOrder({ "name", "componentTransform" })
public class PineTree  extends GameObject2D {

    private String name;
    private Vector3 position = getComponentTransform().getPosition();

    public PineTree(){
        this.name = "PineTree";
        this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("pine_tree"));
    }

}
