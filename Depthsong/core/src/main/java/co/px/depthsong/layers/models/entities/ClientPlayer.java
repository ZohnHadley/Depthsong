package co.px.depthsong.layers.models.entities;

import co.px.depthsong.gameUtils.StructGameSprites;
import co.px.depthsong.layers.models.abstractClasses.Player;
import com.google.gson.annotations.Expose;
import lombok.*;

@Getter
@Setter
public class ClientPlayer extends Player {

    private  static ClientPlayer instance;
    @Expose
    private String name;
    @Expose
    private int healthPoints;


    public void respawn(){}

    private ClientPlayer(){
        this.name = "Player";
        this.healthPoints = 15;
        this.setSprite(StructGameSprites.getInstance().getSprite("viking"));
    }

    public static ClientPlayer getInstance(){
        if(instance == null){
            instance = new ClientPlayer();
        }
        return instance;
    }
}
