package co.px.depthsong.ECS.runtime.systems;

import co.px.depthsong.ECS.core.EntityContext;
import co.px.depthsong.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.core.interfaces.BaseScript;
import co.px.depthsong.engineCore.engine_managers.GameManager;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class UpdateSystem {

    private final EntityContext context = EntityContext.getInstance();

    private static UpdateSystem instance;

    private UpdateSystem() {
    }

    public static UpdateSystem getInstance() {
        if (instance == null) {
            instance = new UpdateSystem();
        }
        return instance;
    }

    public void update(float deltaTime) {
        for(EcsEntity entity : context.getEntities().values()){
            if(BaseScript.class.isAssignableFrom(entity.getClass())){
                ((BaseScript)entity).Update(deltaTime);
            }
        }
    }

}
