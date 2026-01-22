package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.runtime.components.ComponentBoxCollider;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class DTOComponentBoxCollider {

    private final DTOVector2 size;
    private final DTOVector2 position;
    private final boolean isSolid;

    public DTOComponentBoxCollider(Vector2 size, Vector2 position, Boolean isSolid){
        this.size = DTOVector2.fromVector2(size);
        this.position = DTOVector2.fromVector2(position);
        this.isSolid = isSolid;
    }

    public static  ComponentBoxCollider fromDTO(DTOComponentBoxCollider component){
        Vector2 size = DTOVector2.toVector2(component.getSize());
        Vector2 position = DTOVector2.toVector2(component.getPosition());
        return new ComponentBoxCollider(size, position, component.isSolid());
    }

    public static DTOComponentBoxCollider toDTO(ComponentBoxCollider component){
        return new DTOComponentBoxCollider(component.getSize(),component.getPosition(), component.isSolid());
    }
}
