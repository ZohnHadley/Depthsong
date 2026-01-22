package co.px.depthsong.engin.ECS.runtime.components;

import co.px.depthsong.engin.ECS.core.abstractClasses.EcsComponent;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComponentBoxCollider extends EcsComponent {

    private Vector2 size = new Vector2(1, 1);
    private Vector2 position = new Vector2(0,0);
    private boolean isSolid = false;

    public List<Vector2> getCorners(){
        return new ArrayList<>(){
            {
                add(new Vector2(0,0));
                add(new Vector2((float) (1* size.x),0));
                add(new Vector2((float) 0, (float) (1* size.y)));
                add(new Vector2((float) (1* size.x), (float) (1* size.y)));
            }
        };
    }
}
