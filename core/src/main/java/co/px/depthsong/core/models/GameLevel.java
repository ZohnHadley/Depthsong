package co.px.depthsong.core.models;

import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.core.models.abstractClasses.Tile;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class GameLevel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final EntityContext context = EntityContext.getInstance();
    public List<GameObject2D> gameLevelEntities;
    public List<Tile> gameLevelTiles;

    public GameLevel(){
        gameLevelEntities =  new ArrayList<>();
        gameLevelTiles =  new ArrayList<>();
    }
}
