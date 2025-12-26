package co.px.depthsong.ECS.components.DTO;

import co.px.depthsong.ECS.components.runtime.ComponentSprite;
import com.badlogic.gdx.math.Vector2;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
public class DTO_ComponentSprite {

    private final String spriteFileName;
    private final DTO_Vector2 position;

    public DTO_ComponentSprite(String spriteFileName, Vector2 position){
        this.spriteFileName = spriteFileName;
        this.position = DTO_Vector2.fromVector2(position);
    }

//    public static ComponentSprite fromDTO(DTO_ComponentSprite component){
//        Vector2 position = DTO_Vector2.toVector2(component.getPosition());
//        return new ComponentSprite(spriteFileName, position)
//    }
}
