package co.px.depthsong.ECS.abstractClasses;

import co.px.depthsong.ECS.components.ComponentCubeCollider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EcsComponent {
    private EcsEntity parentEntity;
}
