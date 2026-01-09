package co.px.depthsong.engineCore.models.entities;

import co.px.depthsong.enginUtils.GameSprites;
import co.px.depthsong.engineCore.models.abstractClasses.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@JsonIncludeProperties({ "name", "healthPoints", "componentTransform" })
@JsonPropertyOrder({ "name", "healthPoints", "componentTransform" })
public class ClientPlayer extends Player {

    @JsonIgnore
    private static ClientPlayer instance;

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
