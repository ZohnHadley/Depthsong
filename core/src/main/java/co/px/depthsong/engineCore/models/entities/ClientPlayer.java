package co.px.depthsong.engineCore.models.entities;

import co.px.depthsong.ECS.core.interfaces.BaseScript;
import co.px.depthsong.enginUtils.GameSprites;
import co.px.depthsong.engineCore.models.GameObject2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@JsonIncludeProperties({ "name", "healthPoints", "componentTransform" })
@JsonPropertyOrder({ "name", "healthPoints", "componentTransform" })
public class ClientPlayer extends GameObject2D {

    @JsonIgnore
    private static ClientPlayer instance;

    private String name;
    private int healthPoints;
    private Vector3 position = getComponentTransform().getPosition();

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

    @Override
    public void Start(){}

    @Override
    public void Update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) position.y += 5 * deltaTime * 10f;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= 5 * deltaTime * 10f;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) position.x += 5 * deltaTime * 10f;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= 5 * deltaTime * 10f;
    }

    public void respawn() {
    }
}
