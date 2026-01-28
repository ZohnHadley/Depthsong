package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.DTO.util.DTO;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DTOVector2 extends DTO<Vector2> {
    private float data_x = 0;
    private float data_y = 0;

    public static DTOVector2 toDTO(Vector2 vector2){
        return new DTOVector2(vector2.x, vector2.y);
    }

    @Override
    public Vector2 toObject(){
        return new Vector2(this.data_x, this.data_y);
    }
}
