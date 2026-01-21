package co.px.depthsong.engineCore.models.entities;

import co.px.depthsong.enginUtils.GameSprites;
import co.px.depthsong.engineCore.models.GameObject2D;
import com.badlogic.gdx.graphics.Color;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class OtherPlayer extends GameObject2D {
    private Long serverId;
    public OtherPlayer(){
        this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("skier_default"));

    }
}
