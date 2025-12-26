package co.px.depthsong.ECS.components.DTO;

import co.px.depthsong.ECS.components.runtime.ComponentTransform;
import com.badlogic.gdx.math.Vector3;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class DTO_ComponentTransform{

    private final DTO_Vector3 size;
    private final DTO_Vector3 position;
    private final DTO_Vector3 rotation;

    public DTO_ComponentTransform(Vector3 size, Vector3 position, Vector3 rotation){
        this.size = DTO_Vector3.fromVector3(size);
        this.position = DTO_Vector3.fromVector3(position);
        this.rotation = DTO_Vector3.fromVector3(rotation);
    }

    public static ComponentTransform fromDTO(DTO_ComponentTransform component) {
        Vector3 size = new Vector3(DTO_Vector3.toVector3(component.getSize()));
        Vector3 position = new Vector3(DTO_Vector3.toVector3(component.getPosition()));
        Vector3 rotation =  new Vector3(DTO_Vector3.toVector3(component.getRotation()));
        return new ComponentTransform(size, position, rotation);
    }

    public static DTO_ComponentTransform toDTO(ComponentTransform component) {
        return new DTO_ComponentTransform(component.getSize(), component.getPosition(), component.getRotation());
    }

}
