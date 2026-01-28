package co.px.depthsong.game.models.entities;

import co.px.depthsong.engin.enginUtils.GameSprites;
import co.px.depthsong.game.models.GameObject2D;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
@JsonIncludeProperties({ "name", "healthPoints", "componentTransform", "componentBoxCollider", "componentSprite" })
@JsonPropertyOrder({ "name", "healthPoints", "componentTransform", "componentBoxCollider", "componentSprite" })
public class OtherPlayer extends GameObject2D {
    private Long serverId;
    public OtherPlayer(){
        this.getComponentSprite().setSprite("skier_default");
    }
}
