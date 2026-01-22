package co.px.depthsong.game.models.entities;

import co.px.depthsong.engin.ECS.core.abstractClasses.EcsEntity;
import co.px.depthsong.engin.ECS.core.interfaces.BaseScript;
import co.px.depthsong.game.models.Level.GroundColumn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Getter
public class LevelGen extends EcsEntity implements BaseScript {

    private static LevelGen instance;
    private HashMap<Float, List<GroundColumn>> columns = new HashMap<>();

    public static LevelGen getInstance(){
        if(instance == null){
            instance = new LevelGen();
        }
        return instance;
    }

    @Override
    public void Start() {
        Gdx.app.log("Levelgen", "generating level ...");


        float frequency = 0.05f;  // How fast the wave moves horizontally
        float height_offset = 0.1f;

        int min = 32;
        int max = 70;
        Random rand = new Random();

        for(int row_index =0; row_index<5000; row_index++){
            float row_width = rand.nextFloat((200 - 180) + 1) + 180;
            float amplitude = rand.nextFloat((max - min) + 1) + min;
            float offset = (float) (Math.sin(row_index * frequency) * amplitude);

            float row_x_position_adjusted = -(row_width + offset)*0.5f;

            float[] choices = {0,0.2f};
            columns.put(row_x_position_adjusted, new ArrayList<>());
            for(int column_x_position = 0; column_x_position < row_width; column_x_position++){
                height_offset = rand.nextFloat((3f - 1.5f) + 1f) + 2f;
                int index1 = rand.nextInt(2);
                float choice1 = choices[index1];

                Color col = (new Color(choice1+0.55f, choice1+0.65f,choice1+.85f,1f));


                GroundColumn column = new GroundColumn();
                column.setColor(col);
                column.setDimension(2,(6-height_offset));
                column.setPosition(column_x_position+row_x_position_adjusted, row_index);
//                column.setDimension(3f,height_offset);
                columns.get(row_x_position_adjusted).add(column);
            }



//            columns.add(new Vector2(-x_width*0.5f + offset, 32+height_offset));
        }
    }

    @Override
    public void Update(float deltaTime) {

    }
}
