package co.px.depthsong.engineCore.services.level;

import co.px.depthsong.engineCore.engine_managers.GameLevelManager;
import co.px.depthsong.engineCore.models.GameLevel;
import co.px.depthsong.enginUtils.JsonUtil;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

public class GameLevelService {
    private static GameLevelService instance;

    private final JsonUtil json = JsonUtil.getInstance();
    private final GameLevelManager gameLevelManager = GameLevelManager.getInstance();
    private final String levelsFolderPath = "core/src/gameLevels/";

    private List<GameLevel> listLevels = new ArrayList<>();

    private GameLevelService(){

    }

    public static GameLevelService getInstance(){
        if(instance == null){
            instance = new GameLevelService();
        }
        return instance;
    }

    public void loadAll(){
        if(!Gdx.files.local(levelsFolderPath).isDirectory()){
            throw  new RuntimeException("Failure to load levels");
        }

        Gdx.app.log("GameLevelService", "Loading Levels ...");

        //TODO: (polishing) later add game level file name structure like level_fileName.json and check that it matches this pattern
        for(FileHandle file : Gdx.files.local(levelsFolderPath).list()){
            if(file.extension().equalsIgnoreCase("json")){
                GameLevel level = json.fromJson(file.readString(), GameLevel.class);
                Gdx.app.log("GameLevelService", "Loading "+level.getTitle());
                listLevels.add(level);
            }
        }
    }

    public void save(GameLevel level){
        Gdx.app.log("le json ", json.toJson(level));
        Gdx.files.local(levelsFolderPath+level.getTitle()+".json").writeString(json.toJson(level), false);
    }
}
