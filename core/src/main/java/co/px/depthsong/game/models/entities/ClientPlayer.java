package co.px.depthsong.game.models.entities;

import co.px.depthsong.engin.enginUtils.GameSprites;
import co.px.depthsong.game.models.GameObject2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private Sprite sprite;
    private Vector3 position = getComponentTransform().getPosition();

    private float speed = 25;
    private float velocity = 0;
    private float gravity = 9.8f;

    private ClientPlayer(){
        this.name = "Player";
        this.healthPoints = 15;
        this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("skier_default"));
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

        this.getComponentSprite().getSprite().setFlip(false,false);
        this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("skier_default"));
        position.y -= gravity * deltaTime ;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * deltaTime;
            this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("skier_braking"));

        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * deltaTime;
            this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("skier_speeding"));

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed * deltaTime ;
            position.y += speed * 0.2f * deltaTime;
            this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("skier_turning"));
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * deltaTime ;
            position.y += speed * 0.2f * deltaTime;
            this.getComponentSprite().setSprite(GameSprites.getInstance().getSprite("skier_turning"));
            this.getComponentSprite().getSprite().setFlip(true,false);
        }

        position.y -= speed * deltaTime ;
        this.getComponentSprite().getSprite().setColor(Color.FIREBRICK);
    }

    public void respawn() {
    }
}
