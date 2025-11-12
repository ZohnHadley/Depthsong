package co.px.depthsong.gameUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GeneralUtils {

    public static float display_width = Gdx.graphics.getWidth();
    public static float display_height = Gdx.graphics.getHeight();


    public static Vector2 vector3ToVector2(Vector3 vector3){
        return new Vector2(vector3.x, vector3.y);
    }

    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    public static Vector2 lerp(Vector2 v1, Vector2 v2, float t) {
        Vector2 result = new Vector2();
        result.x = (float) GeneralUtils.lerp(v1.x,v2.x,t);
        result.y = (float) GeneralUtils.lerp(v1.y,v2.y,t);
        return result;
    }

    public static Vector2 lerp(Vector2 v1, Vector3 v2, float t) {
        Vector2 result = new Vector2();
        result.x = (float) GeneralUtils.lerp(v1.x,v2.x,t);
        result.y = (float) GeneralUtils.lerp(v1.y,v2.y,t);
        return result;
    }

    public static Vector3 lerp(Vector3 v1, Vector3 v2, float t) {
        Vector3 result = new Vector3();
        result.x = (float) GeneralUtils.lerp(v1.x,v2.x,t);
        result.y = (float) GeneralUtils.lerp(v1.y,v2.y,t);
        result.z = (float) GeneralUtils.lerp(v1.z,v2.z,t);
        return result;
    }

    public static Vector3 lerp(Vector3 v1, Vector2 v2, float t) {
        Vector3 result = new Vector3();
        result.x = (float) GeneralUtils.lerp(v1.x,v2.x,t);
        result.y = (float) GeneralUtils.lerp(v1.y,v2.y,t);
        return result;
    }
}
