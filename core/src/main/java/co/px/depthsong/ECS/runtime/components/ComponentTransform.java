package co.px.depthsong.ECS.runtime.components;

import co.px.depthsong.ECS.core.abstractClasses.EcsComponent;
import com.badlogic.gdx.math.Vector3;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComponentTransform extends EcsComponent {
    private Vector3 size =  new Vector3(0,0,0);
    private Vector3 position =  new Vector3(0,0,0);
    private Vector3 rotation =   new Vector3(0,0,0);

    public void setPosition(float x, float y, float z){
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public Vector3 getCenter() {
        return new Vector3(
            position.x + size.x * 0.5f,
            position.y + size.y * 0.5f,
            position.z + size.z * 0.5f
        );
    }
}
