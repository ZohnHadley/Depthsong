package co.px.depthsong.ECS.components;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComponentCubeCollider extends EcsComponent {

    @Expose
    private Vector3 size = new Vector3(1, 1,  1);
    @Expose
    private Vector2 position = new Vector2(0,0);
    @Expose
    private List<Vector2> corners = new ArrayList<>(){
        {
            add(new Vector2(0,0));
            add(new Vector2((float) (1* size.x),0));
            add(new Vector2((float) 0, (float) (1* size.y)));
            add(new Vector2((float) (1* size.x), (float) (1* size.y)));
        }
    };

    private boolean isSolid = false;

    public Vector2 getCenter(){
        Vector2 center = new Vector2();
        center.set(this.position.x * 0.5f, this.position.y * 0.5f);
        return center;
    }

    public void setSize(Vector3 size){
        this.size = size;
        updateCorners();
    }

    public void setSize(Vector2 size){
        this.size = new Vector3(size.x, size.y, 0);
        updateCorners();
    }

    public void setPosition(Vector2 position){
        this.position = position;
        updateCorners();
    }

    public void setPosition( float x, float y){
        this.position = new Vector2(x, y);
        updateCorners();
    }

    private void updateCorners(){
        corners.clear();
        corners.add(new Vector2(0 + position.x,0 + position.y));
        corners.add(new Vector2((float) (1 * size.x + position.x),0 + position.y));
        corners.add(new Vector2(0 + position.x, (float) (1 * size.y+ position.y)));
        corners.add(new Vector2((float) (1 * size.x + position.x), (float) (1 * size.y + position.y)));
    }
}
