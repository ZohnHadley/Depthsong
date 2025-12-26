package co.px.depthsong.ECS.DTO;

import co.px.depthsong.ECS.runtime.components.ComponentBoxCollider;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class DTO_ComponentBoxCollider {

    private final DTO_Vector2 size;
    private final DTO_Vector2 position;
    private final boolean isSolid;

    public DTO_ComponentBoxCollider(Vector2 size, Vector2 position, Boolean isSolid){
        this.size = DTO_Vector2.fromVector2(size);
        this.position = DTO_Vector2.fromVector2(position);
        this.isSolid = isSolid;
    }

    public static  ComponentBoxCollider fromDTO(DTO_ComponentBoxCollider component){
        Vector2 size = DTO_Vector2.toVector2(component.getSize());
        Vector2 position = DTO_Vector2.toVector2(component.getPosition());
        return new ComponentBoxCollider(size, position, component.isSolid());
    }

    public static DTO_ComponentBoxCollider toDTO(ComponentBoxCollider component){
        return new DTO_ComponentBoxCollider(component.getSize(),component.getPosition(), component.isSolid());
    }
}
