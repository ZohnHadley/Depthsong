package co.px.depthsong.ECS.core.abstractClasses;

import co.px.depthsong.ECS.core.ComponentList;
import co.px.depthsong.ECS.core.EntityContext;
import co.px.depthsong.engineCore.engine_managers.GameManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@Getter
@Setter
public abstract class EcsEntity {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private final EntityContext context = EntityContext.getInstance();
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private final GameManager gameManager = GameManager.getInstance();

    private Long id = 0L;
    private String name;

    private ComponentList componentList;

    public EcsEntity()
    {
        name = "Untitled_Entity";
        componentList = new ComponentList(this.getId());

        context.addEntity(this);
    }

}

