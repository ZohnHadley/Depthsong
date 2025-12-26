package co.px.depthsong.engineCore.models.entities;

import co.px.depthsong.engineCore.models.abstractClasses.Player;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OtherPlayer extends Player {
    private Long serverId;
}
