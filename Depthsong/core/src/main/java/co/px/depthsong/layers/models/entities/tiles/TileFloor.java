package co.px.depthsong.layers.models.entities.tiles;

import co.px.depthsong.gameUtils.StructGameSprites;
import co.px.depthsong.layers.models.abstractClasses.Tile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TileFloor extends Tile {
    private String name;

    public TileFloor(){
        this.name = "Floor";
        this.setSprite(StructGameSprites.getInstance().getSprite("floor"));
    }

    public TileFloor(String name){
        this.name = name;
        this.setSprite(StructGameSprites.getInstance().getSprite("floor"));
    }
}
