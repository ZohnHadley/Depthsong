package co.px.depthsong.ECS.entityContext;


import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.layers.models.entities.ClientPlayer;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Getter
public class EntityContext {

    private static EntityContext instance = null;
    private static Long contextEntityCount = 0L;
    private Hashtable<Long, EcsEntity> entities = new Hashtable<>();
    //organises entities into groups based on components they have
    private Hashtable<String, List<EcsEntity>> entityGroups = new Hashtable<>();
    private EcsEntity player;

    private EntityContext() { }

    public static EntityContext getInstance() {
        if (instance == null) {
            instance = new EntityContext();
        }
        return instance;
    }

    public void addEntity(EcsEntity ecsEntity) {

        ecsEntity.setId(contextEntityCount);
         entities.put(ecsEntity.getId(), ecsEntity);
        contextEntityCount++;
    }

    public EcsEntity createEntity(EcsEntity ecsEntity) {
        if (ecsEntity == null) {
            throw new NullPointerException("Entity cannot be null.");
        }

        if (entities.contains(ecsEntity.getId())) {
            //get new ID for the entity
            throw new RuntimeException("Entity with ID " + ecsEntity.getId() + " already exists.");
        }

        ecsEntity.setId(contextEntityCount);
        entities.put(ecsEntity.getId(), ecsEntity);
        contextEntityCount++;


        return ecsEntity;
    }

    public void removeEntity(EcsEntity ecsEntity) {
        if (ecsEntity == null) {
            throw new NullPointerException("Entity cannot be null.");
        }

        if (!entities.contains(ecsEntity.getId())) {
            throw new RuntimeException("Entity with ID " + ecsEntity.getId() + " does not exist.");
        }

        entities.remove(ecsEntity.getId());
    }

    public EcsEntity getEntity(long id) {
        if (entities.contains(id)) {
            return entities.get(id);
        } else {
            return null;
        }
    }
    public EcsEntity getPlayer() {
        if(entities.isEmpty()) {
            return null;
        }
        if (player == null) {
            player = getAllEntitiesOfType(ClientPlayer.class).getFirst();
        }
        return player;
    }

    // public List<Entity> getAllEntitiesFromGroup(String group) {
    //     if (entityGroups.contains(group)) {
    //         return entityGroups[group];
    //     } else {
    //         return new List<Entity>();
    //     }
    // }
    public List<EcsEntity> getAllEntitiesOfType(Type param_entity_type){
        if(param_entity_type == null){
            throw new NullPointerException("EntityType cannot be null.");
        }
        if(entities.isEmpty()){
//            throw new NullPointerException("Entity list is empty.");
            return new ArrayList<>();
        }

        List<EcsEntity> result = new ArrayList<>();
        for (var entity : entities.values()) {
            if (entity.getClass().equals(param_entity_type)) {
                result.add(entity);
            }
        }
        return result;
    }

    public List<EcsEntity> getAllEntitiesWithComponent(Type param_component_type){
        List<EcsEntity> result = new ArrayList<>();

        for (EcsEntity ecsEntity : entities.values()) {
            if (ecsEntity.getComponentList().has(param_component_type)) {
                result.add(ecsEntity);
            }
        }

        return result;
    }
    //TODO fix might cause error
    public List<EcsEntity> getAllEntitiesWithComponents(List<Type> ComponentTypes)
    {
        List<EcsEntity> result = new ArrayList<>();

        for (EcsEntity ecsEntity : entities.values())
        {
            boolean hasAllComponents = true;
            for (Type componentType : ComponentTypes)
            {
                if (!ecsEntity.getComponentList().has((Type) ComponentTypes))
                {
                    hasAllComponents = false;
                    //when last component is not found (has all == false), break out of the loop and return a empty list
                    break;
                }
            }

            if (hasAllComponents)
            {
                result.add(ecsEntity);
            }
        }

        return result;
    }

    //get all groups
    public Hashtable<String, List<EcsEntity>> getGroups() {
        return entityGroups;
    }

    public void clearContext(){
        entityGroups.clear();
        entities.clear();
    }
}
