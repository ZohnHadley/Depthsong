package co.px.depthsong.core.models.entities.tiles;

import co.px.depthsong.enginUtils.GameSprites;
import co.px.depthsong.core.models.abstractClasses.Tile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TileWall extends Tile {
    private String name;

    public TileWall(){
        this.name = "Wall";
        this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("wall"));
    }

    public TileWall(String name){
        this.name = name;
        this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("wall"));
    }
}
