package co.px.depthsong.ECS.components;

import co.px.depthsong.ECS.abstractClasses.EcsComponent;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.annotations.Expose;
import lombok.*;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComponentCubeCollider extends EcsComponent {

    @Setter(AccessLevel.NONE)
    @Expose
    private Dimension2D dimensions = new Dimension(1,1);
    @Setter(AccessLevel.NONE)
    @Expose
    private Vector2 position = new Vector2(0,0);
    @Setter(AccessLevel.NONE)
    @Expose
    private List<Vector2> corners = new ArrayList<>(){
        {
            add(new Vector2(0,0));
            add(new Vector2((float) (1* dimensions.getWidth()),0));
            add(new Vector2((float) 0, (float) (1* dimensions.getHeight())));
            add(new Vector2((float) (1* dimensions.getWidth()), (float) (1* dimensions.getHeight())));
        }
    };

    private boolean isSolid = false;

    public Vector2 getCenter(){
        Vector2 center = new Vector2();
        center.set(this.position.x * 0.5f, this.position.y * 0.5f);
        return center;
    }

    public void setDimensions(Dimension2D dimensions){
        this.dimensions = dimensions;
        updateCorners();
    }

    public void setDimensions(int width, int height){
        this.dimensions = new Dimension(width, height);
        updateCorners();
    }

    public void setPosition(Vector2 position){
        this.position = position;
        updateCorners();
    }

    public void setPosition( float x, float y){
        this.position = new Vector2(x, y);
        updateCorners();
    }

    private void updateCorners(){
        corners.clear();
        corners.add(new Vector2(0 + position.x,0 + position.y));
        corners.add(new Vector2((float) (1 * dimensions.getWidth() + position.x),0 + position.y));
        corners.add(new Vector2(0 + position.x, (float) (1 * dimensions.getHeight()+ position.y)));
        corners.add(new Vector2((float) (1 * dimensions.getWidth() + position.x), (float) (1 * dimensions.getHeight() + position.y)));
    }
}
