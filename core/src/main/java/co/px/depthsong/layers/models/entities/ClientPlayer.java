package co.px.depthsong.layers.models.entities;

import co.px.depthsong.enginUtils.GameSprites;
import co.px.depthsong.layers.models.abstractClasses.Player;
import lombok.*;

@Getter
@Setter
public class ClientPlayer extends Player {

    private  static ClientPlayer instance;
    private String name;
    private int healthPoints;


    public void respawn(){}

    private ClientPlayer(){
        this.name = "Player";
        this.healthPoints = 15;
        this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("viking"));
    }

    public static ClientPlayer getInstance(){
        if(instance == null){
            instance = new ClientPlayer();
        }
        return instance;
    }
}
