package co.px.depthsong.engineCore.models.entities;

import co.px.depthsong.engineCore.models.GameObject2D;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OtherPlayer extends GameObject2D {
    private Long serverId;
}
