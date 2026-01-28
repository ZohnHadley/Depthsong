package co.px.depthsong.engin.ECS.DTO.util;

import co.px.depthsong.engin.ECS.DTO.DTOComponentList;
import co.px.depthsong.engin.ECS.core.ComponentList;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public abstract class DTO <O> {


    public static <O, D extends DTO> D toDTO(O object){
        return null;
    };

    public abstract O toObject();

}
