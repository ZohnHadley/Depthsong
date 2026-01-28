package co.px.depthsong.engin.ECS.core.abstractClasses;

import co.px.depthsong.engin.ECS.core.ComponentList;
import co.px.depthsong.engin.ECS.core.EntityContext;
import co.px.depthsong.engin.engineCore.engine_managers.GameManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@ToString
@Getter
@Setter
public class EcsEntity {

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

    public EcsEntity(String name, ComponentList componentList){
        this.name = name;
        this.componentList = componentList;

        context.addEntity(this);
        this.componentList.setEntityID(this.id);
    }

}

