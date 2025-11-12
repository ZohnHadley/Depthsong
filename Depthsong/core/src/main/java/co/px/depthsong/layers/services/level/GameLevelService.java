package co.px.depthsong.layers.services.level;

import co.px.depthsong.layers.models.GameLevel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class GameLevelService {
    private static GameLevelService instance;
    private final GsonBuilder builder = new GsonBuilder();
    private Gson gson = builder.create();

    private final GameLevelManager  gameLevelManager = GameLevelManager.getInstance();
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

    public void loadAllPrebuiltLevels(){
        if(!Gdx.files.local(levelsFolderPath).isDirectory()){
            throw  new RuntimeException("Failure to load levels");
        }
        for(FileHandle file : Gdx.files.local(levelsFolderPath).list()){
            if(file.extension().equalsIgnoreCase("json")){
//                gson.fromJson(file.readString(), GameLevel.class);
//                Gdx.app.log("GameLevelService", ""+file.readString());
            }

        }
    }
}
