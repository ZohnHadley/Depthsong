package co.px.depthsong.engin.ECS.DTO;

import co.px.depthsong.engin.ECS.DTO.util.DTO;
import co.px.depthsong.engin.ECS.core.ComponentList;
import co.px.depthsong.engin.ECS.core.abstractClasses.EcsComponent;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DTOComponentList extends DTO<ComponentList> {

    private final Long data_entityID;
    private List<DTO> data_componentsList = new ArrayList<>();

    public static DTOComponentList toDTO(ComponentList componentList){
        List<EcsComponent> componentsList = new ArrayList<>();
        for(EcsComponent component : componentList.getComponentsList()){

        }
         return null;
    }

    @Override
    public ComponentList toObject() {
        return null;
    }
}
