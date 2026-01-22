package co.px.depthsong.engin.ECS.DTO;

import com.badlogic.gdx.math.Vector3;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DTOVector3 {
    private float x = 0;
    private float y = 0;
    private float z = 0;

    public static DTOVector3 fromVector3(Vector3 vector3){
        return new DTOVector3(vector3.x, vector3.y, vector3.z);
    }
    public static Vector3 toVector3(DTOVector3 dto){
        return new Vector3(dto.x, dto.y, dto.z);
    }
}
