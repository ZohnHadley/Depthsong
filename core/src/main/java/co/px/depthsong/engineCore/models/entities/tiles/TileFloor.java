package co.px.depthsong.engineCore.models.entities.tiles;

import co.px.depthsong.enginUtils.GameSprites;
import co.px.depthsong.engineCore.models.abstractClasses.Tile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TileFloor extends Tile {
    private String name;

    public TileFloor(){
        this.name = "Floor";
        this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("floor"));
    }
}
