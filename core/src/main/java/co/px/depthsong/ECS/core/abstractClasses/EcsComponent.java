package co.px.depthsong.ECS.core.abstractClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EcsComponent {
    private EcsEntity parentEntity;
}
