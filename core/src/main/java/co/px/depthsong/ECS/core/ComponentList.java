package co.px.depthsong.ECS.core;

import co.px.depthsong.ECS.core.abstractClasses.EcsComponent;
import co.px.depthsong.ECS.core.abstractClasses.EcsEntity;
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
    private final Long entityID;

    @Getter
    private List<EcsComponent> componentsList = new ArrayList<>();

    public ComponentList(Long entityID){
        this.entityID = entityID;
    }

    public void add(EcsComponent param_Ecs_component)
    {
        if (param_Ecs_component == null)
        {
            Gdx.app.log("","Component cannot be null.");
            return;
        }

        if (componentsList.contains(param_Ecs_component))
        {
            Gdx.app.log("","Component already exists in entity.");
            return;
        }

        if (!context.getGroups().contains(param_Ecs_component.getClass().getTypeName()))
        {
            context.getGroups().put(param_Ecs_component.getClass().getTypeName(), new ArrayList<>());
        }

        componentsList.add(param_Ecs_component);
        EcsEntity entity = context.getEntity(entityID);


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

        if (!componentsList.contains(param_Ecs_component))
        {
            throw new RuntimeException("Component not found in entity.");
        }


        componentsList.remove(param_Ecs_component);
        EcsEntity entity = context.getEntity(entityID);

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

    public EcsComponent get(Type param_component_type)
    {
        if  (param_component_type == null)
        {
            throw new NullPointerException("getComponent:" + " Component type cannot be null.");
        }
        for(EcsComponent ecsComponent : componentsList){
            if(ecsComponent.getClass() == param_component_type){
                return ecsComponent;
            }
        }
        return null;
    }

    public Boolean has(Class type)
    {
        if (type == null)
        {
            throw new NullPointerException("");
        }

        for (EcsComponent ecsComponent : componentsList)
        {
            if (type.isAssignableFrom(ecsComponent.getClass()))
            {
                return true;
            }
        }
        return false;
    }
}
