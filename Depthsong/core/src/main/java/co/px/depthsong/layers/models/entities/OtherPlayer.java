package co.px.depthsong.layers.models.entities;

import co.px.depthsong.layers.models.abstractClasses.Player;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OtherPlayer extends Player {
    private Long serverId;
}
