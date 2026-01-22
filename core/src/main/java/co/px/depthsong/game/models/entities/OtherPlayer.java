package co.px.depthsong.game.models.entities;

import co.px.depthsong.engin.enginUtils.GameSprites;
import co.px.depthsong.game.models.GameObject2D;
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
