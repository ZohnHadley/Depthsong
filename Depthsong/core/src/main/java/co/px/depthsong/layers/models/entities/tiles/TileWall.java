package co.px.depthsong.layers.models.entities.tiles;

import co.px.depthsong.gameUtils.StructGameSprites;
import co.px.depthsong.layers.models.abstractClasses.Tile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TileWall extends Tile {
    private String name;

    public TileWall(){
        this.name = "Wall";
        this.setSprite(StructGameSprites.getInstance().getSprite("wall"));
    }

    public TileWall(String name){
        this.name = name;
        this.setSprite(StructGameSprites.getInstance().getSprite("wall"));
    }
}
