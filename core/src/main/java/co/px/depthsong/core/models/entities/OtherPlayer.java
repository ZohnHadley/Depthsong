package co.px.depthsong.core.models.entities;

import co.px.depthsong.core.models.abstractClasses.Player;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OtherPlayer extends Player {
    private Long serverId;
}
