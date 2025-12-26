package co.px.depthsong.engineCore.models;

import co.px.depthsong.ECS.core.EntityContext;
import co.px.depthsong.engineCore.models.abstractClasses.Tile;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameLevel {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final EntityContext context = EntityContext.getInstance();

    public String title = "N/A";
    public List<Integer> tilesRepresentation = new ArrayList<>();

    public List<GameObject2D> gameLevelEntities;
    public List<Tile> gameLevelTiles;

    public GameLevel(String title, List<Integer> tilesRepresentation){
        this.title = title;
        this.tilesRepresentation = tilesRepresentation;
    }

    public GameLevel(){
        gameLevelEntities =  new ArrayList<>();
        gameLevelTiles =  new ArrayList<>();
    }
}
