package co.px.depthsong.ECS.components.core;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.entityContext.EntityContext;
import com.badlogic.gdx.Gdx;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ComponentList {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final EntityContext context = EntityContext.getInstance();

    @Getter
    private final EcsEntity entity;

    @Getter
    private List<EcsComponent> list = new ArrayList<>();

    public ComponentList(EcsEntity param_entity){
        entity = param_entity;
    }

    public void add(EcsComponent param_Ecs_component)
    {
        if (param_Ecs_component == null)
        {
            Gdx.app.log("","Component cannot be null.");
            return;
        }

        if (list.contains(param_Ecs_component))
        {
            Gdx.app.log("","Component already exists in entity.");
            return;
        }

        if (!context.getGroups().contains(param_Ecs_component.getClass().getTypeName()))
        {
            context.getGroups().put(param_Ecs_component.getClass().getTypeName(), new ArrayList<>());
        }

        list.add(param_Ecs_component);
        param_Ecs_component.setParentEntity(entity);


        String componentType = param_Ecs_component.getClass().getTypeName();
        if (!context.getGroups().contains(componentType)) {
            context.getGroups().put(componentType, new ArrayList<>());
        }
        context.getGroups().get(param_Ecs_component.getClass().getTypeName()).add(entity);
    }

    public void remove(EcsComponent param_Ecs_component)
    {
        if (param_Ecs_component == null)
        {
            throw new NullPointerException("Component cannot be null.");
        }

        if (!list.contains(param_Ecs_component))
        {
            throw new RuntimeException("Component not found in entity.");
        }

        if (list.contains(param_Ecs_component))
        {
            list.remove(param_Ecs_component);
            param_Ecs_component.setParentEntity(null);

            String componentType = param_Ecs_component.getClass().getTypeName();
            if (context.getGroups().contains(componentType) &&
                context.getGroups().get(componentType).size() > 1) {
                context.getGroups().get(componentType).remove(entity);
            }
            if(context.getGroups().get(componentType).size() <= 1){
                context.getGroups().remove(componentType);
            }

            context.getGroups().get(param_Ecs_component.getClass().getTypeName()).remove(entity);
        }
        else
        {
            throw new RuntimeException("Component not found in entity.");
        }
    }

    public EcsComponent get(Type param_component_type)
    {
        if  (param_component_type == null)
        {
            throw new NullPointerException("getComponent:" + " Component type cannot be null.");
        }
        for(EcsComponent ecsComponent : list){
            if(ecsComponent.getClass() == param_component_type){
                return ecsComponent;
            }
        }
        return null;
    }

    public Boolean has(Type param_component_type)
    {
        if (param_component_type == null)
        {
            throw new NullPointerException("");
        }

        for (EcsComponent ecsComponent : list)
        {
            if (ecsComponent.getClass() == param_component_type)
            {
                return true;
            }
        }
        return false;
    }
}
