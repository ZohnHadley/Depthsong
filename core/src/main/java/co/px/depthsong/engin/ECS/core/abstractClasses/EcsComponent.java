package co.px.depthsong.engin.ECS.core.abstractClasses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EcsComponent {
    private EcsEntity parentEntity;
}
