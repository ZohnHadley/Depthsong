package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.DTO.util.DTO;
import co.px.depthsong.engin.ECS.runtime.components.ComponentBoxCollider;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class DTOComponentBoxCollider extends DTO<ComponentBoxCollider> {

    private final DTOVector2 data_size;
    private final DTOVector2 data_position;
    private final boolean data_isSolid;

    public DTOComponentBoxCollider(Vector2 data_size, Vector2 data_position, Boolean data_isSolid){
        this.data_size = DTOVector2.toDTO(data_size);
        this.data_position = DTOVector2.toDTO(data_position);
        this.data_isSolid = data_isSolid;
    }

    public static DTOComponentBoxCollider toDTO(ComponentBoxCollider component){
        return new DTOComponentBoxCollider(component.getSize(),component.getPosition(), component.isSolid());
    }

    @Override
    public ComponentBoxCollider toObject(){
        Vector2 size = this.getData_size().toObject();
        Vector2 position = this.getData_position().toObject();
        return new ComponentBoxCollider(size, position, this.isData_isSolid());
    }
}
