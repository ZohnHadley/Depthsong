package co.px.depthsong.game.models.Level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import lombok.*;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroundColumn {
    private Vector2 position = new Vector2(0,0);
    private Vector2 dimensions = new Vector2(3,5);
    private Color color = new Color(1,1,1,1);

    public void setPosition(float x, float y){
        position = new Vector2(x,y);
    }

    public void setDimension(float x, float y){
        dimensions = new Vector2(x,y);
    }

    public void setColor(float r, float g, float b, float a){
        color = new Color(r,g,b,a);
    }
}
