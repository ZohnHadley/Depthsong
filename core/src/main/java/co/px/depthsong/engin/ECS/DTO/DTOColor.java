package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.DTO.util.DTO;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DTOColor extends DTO<Color> {
    private float data_r = 0;
    private float data_g = 0;
    private float data_b = 0;
    private float data_a = 1;


    public static DTOColor toDTO(Color color){
        return new DTOColor(color.r, color.g, color.b, color.a);
    }

    @Override
    public Color toObject(){
        return new Color(this.data_r, this.data_g, this.data_b, this.data_a);
    }
}
