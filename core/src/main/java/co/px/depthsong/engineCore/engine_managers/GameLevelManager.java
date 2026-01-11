package co.px.depthsong.engineCore.engine_managers;

import co.px.depthsong.engineCore.models.GameLevel;
import co.px.depthsong.enginUtils.JsonUtil;
import co.px.depthsong.engineCore.models.abstractClasses.Tile;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

public class GameLevelManager {
    private static GameLevelManager instance;

    private final JsonUtil json = JsonUtil.getInstance();
    private final String levelsFolderPath = "core/src/gameLevels/";

    private List<GameLevel> listLevels = new ArrayList<>();

    private GameLevelManager(){

    }

    public static GameLevelManager getInstance(){
        if(instance == null){
            instance = new GameLevelManager();
        }
        return instance;
    }

    public List<Tile> readLevelTileJson(){
        return new ArrayList<>();
    }

    public void loadAll(){
        if(!Gdx.files.local(levelsFolderPath).isDirectory()){
            throw  new RuntimeException("Failure to load levels");
        }

        Gdx.app.log("GameLevelManager", "Loading Levels ...");

        //TODO: (polishing) later add game level file name structure like level_fileName.json and check that it matches this pattern
        for(FileHandle file : Gdx.files.local(levelsFolderPath).list()){
            if(file.extension().equalsIgnoreCase("json")){

                JsonNode level = json.fromJson(file.readString());

                Gdx.app.log("GameLevelService", "Loading "+ level.get("tilesRepresentation"));
//                listLevels.add(level);
            }
        }
    }

    public void save(GameLevel level){
        Gdx.app.log("le json ", json.toJson(level));
        Gdx.files.local(levelsFolderPath+level.getTitle()+".json").writeString(json.toJson(level), false);
    }
}
