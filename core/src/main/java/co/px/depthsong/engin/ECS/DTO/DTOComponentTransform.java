package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.DTO.util.DTO;
import co.px.depthsong.engin.ECS.runtime.components.ComponentTransform;
import com.badlogic.gdx.math.Vector3;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class DTOComponentTransform extends DTO<ComponentTransform> {

    private final DTOVector3 data_size;
    private final DTOVector3 data_position;
    private final DTOVector3 data_rotation;

    public DTOComponentTransform(Vector3 data_size, Vector3 data_position, Vector3 data_rotation){
        this.data_size = DTOVector3.toDTO(data_size);
        this.data_position = DTOVector3.toDTO(data_position);
        this.data_rotation = DTOVector3.toDTO(data_rotation);
    }


    public static DTOComponentTransform toDTO(ComponentTransform component) {
        return new DTOComponentTransform(component.getSize(), component.getPosition(), component.getRotation());
    }

    @Override
    public ComponentTransform toObject() {
        Vector3 size = this.getData_size().toObject();
        Vector3 position = this.getData_position().toObject();
        Vector3 rotation =  this.getData_rotation().toObject();
        return new ComponentTransform(size, position, rotation);
    }

}
