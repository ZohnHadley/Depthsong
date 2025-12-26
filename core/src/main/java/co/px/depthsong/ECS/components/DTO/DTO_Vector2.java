package co.px.depthsong.ECS.components.DTO;

import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DTO_Vector2{
    private float x = 0;
    private float y = 0;

    public static DTO_Vector2 fromVector2(Vector2 vector2){
        return new DTO_Vector2(vector2.x, vector2.y);
    }
    public static Vector2 toVector2(DTO_Vector2 dto){
        return new Vector2(dto.x, dto.y);
    }
}
