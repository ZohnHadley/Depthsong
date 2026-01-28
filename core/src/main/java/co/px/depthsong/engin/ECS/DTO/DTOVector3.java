package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.DTO.util.DTO;
import com.badlogic.gdx.math.Vector3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DTOVector3 extends DTO<Vector3> {
    private float data_x = 0;
    private float data_y = 0;
    private float data_z = 0;

    public static DTOVector3 toDTO(Vector3 vector3){
        return new DTOVector3(vector3.x, vector3.y, vector3.z);
    }

    @Override
    public Vector3 toObject(){
        return new Vector3(this.data_x, this.data_y, this.data_z);
    }
}
