package co.px.depthsong.ECS.components;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.awt.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComponentTransform extends EcsComponent {

    private @JsonProperty("size") Vector3 size =  new Vector3(0,0,0);
    private @JsonProperty("position") Vector3 position =  new Vector3(0,0,0);
    private @JsonProperty("rotation") Vector3 rotation =   new Vector3(0,0,0);
    private @JsonProperty("center") Vector3 center =  new Vector3(0,0,0);

    private ComponentTransform parent = null;

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

    @JsonGetter
    public Vector3 getCenter(){
        this.center = new Vector3(this.position.x + (this.size.x * 0.5f), this.position.y + (this.size.y * 0.5f), this.position.z + (this.size.z * 0.5f));
        return this.center;
    }
}
