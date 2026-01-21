package co.px.depthsong.ECS.utils;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MasterShapeRenderer extends ShapeRenderer{

    private static MasterShapeRenderer instance;

    public static MasterShapeRenderer getInstance(){
        if (instance == null){
            instance = new MasterShapeRenderer();
        }
        return instance;
    }
}
