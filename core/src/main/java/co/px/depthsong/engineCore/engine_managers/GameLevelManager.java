package co.px.depthsong.engineCore.engine_managers;

import co.px.depthsong.engineCore.models.GameLevel;

import java.util.List;

public class GameLevelManager {
    private static GameLevelManager instance;
    private List<GameLevel> gameLevels;

    private GameLevelManager(){}

    public static GameLevelManager getInstance(){
        if(instance==null){
            instance = new GameLevelManager();
        }
        return instance;
    }
}
