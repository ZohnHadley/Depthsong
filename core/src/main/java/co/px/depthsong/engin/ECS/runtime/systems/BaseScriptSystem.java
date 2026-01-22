package co.px.depthsong.engin.ECS.runtime.systems;

import co.px.depthsong.engin.ECS.core.EntityContext;
import co.px.depthsong.engin.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.engin.ECS.core.interfaces.BaseScript;

public class BaseScriptSystem {

    private final EntityContext context = EntityContext.getInstance();

    private static BaseScriptSystem instance;

    private BaseScriptSystem() {
    }

    public static BaseScriptSystem getInstance() {
        if (instance == null) {
            instance = new BaseScriptSystem();
        }
        return instance;
    }

    public void start(){
        for(EcsEntity entity : context.getEntities().values()){
            if(BaseScript.class.isAssignableFrom(entity.getClass())){
                ((BaseScript)entity).Start();
            }
        }
    }

    public void update(float deltaTime) {
        for(EcsEntity entity : context.getEntities().values()){
            if(BaseScript.class.isAssignableFrom(entity.getClass())){
                ((BaseScript)entity).Update(deltaTime);
            }
        }
    }

}
