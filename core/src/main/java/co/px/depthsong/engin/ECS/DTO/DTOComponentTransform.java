package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.runtime.components.ComponentTransform;
import com.badlogic.gdx.math.Vector3;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class DTOComponentTransform {

    private final DTOVector3 size;
    private final DTOVector3 position;
    private final DTOVector3 rotation;

    public DTOComponentTransform(Vector3 size, Vector3 position, Vector3 rotation){
        this.size = DTOVector3.fromVector3(size);
        this.position = DTOVector3.fromVector3(position);
        this.rotation = DTOVector3.fromVector3(rotation);
    }

    public static ComponentTransform fromDTO(DTOComponentTransform component) {
        Vector3 size = new Vector3(DTOVector3.toVector3(component.getSize()));
        Vector3 position = new Vector3(DTOVector3.toVector3(component.getPosition()));
        Vector3 rotation =  new Vector3(DTOVector3.toVector3(component.getRotation()));
        return new ComponentTransform(size, position, rotation);
    }

    public static DTOComponentTransform toDTO(ComponentTransform component) {
        return new DTOComponentTransform(component.getSize(), component.getPosition(), component.getRotation());
    }

}
