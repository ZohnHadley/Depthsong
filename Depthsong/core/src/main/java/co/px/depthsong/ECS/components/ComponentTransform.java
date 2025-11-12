package co.px.depthsong.ECS.components;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.annotations.Expose;
import lombok.*;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.Hashtable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComponentTransform extends EcsComponent {

    @Expose
    private Dimension2D scale = new Dimension();
    @Expose
    private Vector3 position = new Vector3(0,0,0);
    @Expose
    private Vector3 rotation = new Vector3(0,0,0);
    @Expose
    private ComponentTransform parent;
    @Expose
    private Hashtable<String, ComponentTransform> children = new Hashtable<>();

    public Vector3 getCenter(){
        Vector3 center = new Vector3();
        center.set(this.position.x * 0.5f, this.position.y * 0.5f, this.position.z * 0.5f);
        return center;
    }

    public void setParent(ComponentTransform parent){
        if(parent == null){
            Gdx.app.error("ComponentTransform", "parent is null");
            return;
        }
        if (parent == getParent()) {
            return;
        }

        this.parent = parent;
        this.position = this.position.add(parent.getPosition());
        this.scale.setSize(new Dimension((int) (this.scale.getWidth() + parent.getScale().getWidth()), (int) (this.scale.getHeight() + parent.getScale().getHeight())));
    }
}
