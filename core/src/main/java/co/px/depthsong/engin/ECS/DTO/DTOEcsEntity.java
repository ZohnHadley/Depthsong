package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.DTO.util.DTO;
import co.px.depthsong.engin.ECS.core.ComponentList;
import co.px.depthsong.engin.ECS.core.abstractClasses.EcsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DTOEcsEntity extends DTO<EcsEntity> {

    private Long data_id;
    private String data_name;
    private ComponentList data_componentList;

    public static DTOEcsEntity toDTO(EcsEntity entity){
        return new DTOEcsEntity(entity.getId(), entity.getName(), entity.getComponentList());
    }

    @Override
    public EcsEntity toObject() {
        return new EcsEntity(this.data_name, this.data_componentList);
    }
}
