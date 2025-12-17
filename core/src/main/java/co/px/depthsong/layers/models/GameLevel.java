package co.px.depthsong.layers.models;

import co.px.depthsong.ECS.entityContext.EntityContext;
import co.px.depthsong.layers.models.abstractClasses.Tile;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@AllArgsConstructor
@Getter
public class GameLevel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final EntityContext context = EntityContext.getInstance();
    @Expose
    public List<GameObject2D> gameLevelEntities;
    @Expose
    public List<Tile> gameLevelTiles;

    public GameLevel(){
        gameLevelEntities =  new ArrayList<>();
        gameLevelTiles =  new ArrayList<>();
    }
}
