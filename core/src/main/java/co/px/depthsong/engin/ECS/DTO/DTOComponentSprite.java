package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.DTO.util.DTO;
import co.px.depthsong.engin.ECS.runtime.components.ComponentSprite;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOComponentSprite extends DTO<ComponentSprite> {

    private final String data_spriteName;

    public DTOComponentSprite(String data_spriteName){
        this.data_spriteName = data_spriteName;
    }

    public static DTOComponentSprite toDTO(ComponentSprite component){
        return new DTOComponentSprite(component.getSpriteName());
    }

    @Override
    public ComponentSprite toObject() {
        return new ComponentSprite(this.data_spriteName);
    }
}
