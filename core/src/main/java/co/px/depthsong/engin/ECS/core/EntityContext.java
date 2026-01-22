package co.px.depthsong.engin.ECS.core;


import co.px.depthsong.engin.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.game.models.entities.ClientPlayer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Getter
public class EntityContext {

    private static EntityContext instance = null;
    private static Long contextEntityCount = 0L;
    private Hashtable<Long, EcsEntity> entities;
    //organises entities into groups based on components they have
    private Hashtable<String, List<EcsEntity>> entityComponentGroups;
    private EcsEntity player;

    private EntityContext() {
        entities = new Hashtable<>();
        entityComponentGroups = new Hashtable<>();
    }

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
        return getAllEntitiesOfType(ClientPlayer.class).getFirst();
    }

    public List<EcsEntity> getAllEntitiesOfType(Class type){
        if(type == null){
            throw new NullPointerException("EntityType cannot be null.");
        }

        List<EcsEntity> result = new ArrayList<>();
        for (EcsEntity entity : entities.values()) {
            if (type.isAssignableFrom(entity.getClass())) {
                result.add(entity);
            }
        }
        return result;
    }

    public List<EcsEntity> getAllEntitiesWithComponent(Class type){
        List<EcsEntity> result = new ArrayList<>();

        for (EcsEntity ecsEntity : entities.values()) {
            if (ecsEntity.getComponentList().has(type)) {
                result.add(ecsEntity);
            }
        }

        return result;
    }

    //get all groups
    public Hashtable<String, List<EcsEntity>> getGroups() {
        return entityComponentGroups;
    }

    public void clearContext(){
        entityComponentGroups.clear();
        entities.clear();
    }
}
