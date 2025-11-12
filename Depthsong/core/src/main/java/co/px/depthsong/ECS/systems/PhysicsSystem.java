package co.px.depthsong.ECS.systems;

import co.px.depthsong.ECS.abstractClasses.EcsEntity;
import co.px.depthsong.ECS.components.ComponentCubeCollider;
import co.px.depthsong.ECS.entityContext.EntityContext;
import com.badlogic.gdx.math.Vector2;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class PhysicsSystem {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final EntityContext context = EntityContext.getInstance();

    private static PhysicsSystem instance = null;
    private PhysicsSystem() {

    }

    public static PhysicsSystem getInstance() {
        if(instance == null){
            instance = new PhysicsSystem();
        }
        return instance;
    }


    public static boolean overlapped(ComponentCubeCollider c1, ComponentCubeCollider c2) {
        float x1 = c1.getPosition().x;
        float y1 = c1.getPosition().y;
        float w1 = (float) c1.getDimensions().getWidth();
        float h1 = (float) c1.getDimensions().getHeight();

        float x2 = c2.getPosition().x;
        float y2 = c2.getPosition().y;
        float w2 = (float) c2.getDimensions().getWidth();
        float h2 = (float) c2.getDimensions().getHeight();

        // Axis-Aligned Bounding Box collision check
        return x1 < x2 + w2 &&
            x1 + w1 > x2 &&
            y1 < y2 + h2 &&
            y1 + h1 > y2;
    }

    public static Vector2 collisionDirection(ComponentCubeCollider c1, ComponentCubeCollider c2) {
        return new Vector2(c2.getPosition().x - c1.getPosition().x, c2.getPosition().y - c1.getPosition().y); // direction
    }

    public void update(float deltaTime){
        for(EcsEntity ent : context.getEntities().values()){

            if(ent.getComponentList().has(ComponentCubeCollider.class)){
                ComponentCubeCollider collider = (ComponentCubeCollider) ent.getComponentList().get(ComponentCubeCollider.class);
                //loop through entities again except the current entity and figure out if its colliding
                System.out.println(context.getEntities().values().iterator().next());
            }
        }
    }
}
