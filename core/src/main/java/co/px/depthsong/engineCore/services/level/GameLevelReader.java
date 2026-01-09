package co.px.depthsong.engineCore.services.level;

import co.px.depthsong.engineCore.models.abstractClasses.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLevelReader {

    private GameLevelReader instance;
    private Map<String, String> legendDetails = new HashMap<>();

    private GameLevelReader(){
        legendDetails.put("floor", "0");
        legendDetails.put("wall", "1");

    }

//    public static List<Tile> readLevelRepresentation(){
//
//    }
}
