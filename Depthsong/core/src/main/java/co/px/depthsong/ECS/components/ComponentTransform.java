package co.px.depthsong.ECS.components;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.annotations.Expose;
import lombok.*;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.Hashtable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComponentTransform extends EcsComponent {

    private Vector3 size =  new Vector3();

    private Vector3 position =  new Vector3();

    private Vector3 center =  new Vector3();

    private Vector3 rotation =   new Vector3();

    private ComponentTransform parent = null;

    private Hashtable<String, ComponentTransform> children = new Hashtable<>();

    public void setPosition(Vector3 position){
        this.position = position;
        this.center.set(position.x + size.x * 0.5f, position.y + size.y * 0.5f, position.z + size.z * 0.5f);
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
        this.size = new Vector3((int) (this.size.x + parent.getSize().x), (int) (this.size.y + parent.getSize().y), 0);
    }
}
