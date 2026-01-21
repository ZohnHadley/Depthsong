package co.px.depthsong.enginUtils;

import co.px.depthsong.engineCore.util.SpriteSheet;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;


public class GameSprites {

    private static GameSprites instance;
    public final Vector2 dimension = new Vector2(16, 16);
    private SpriteSheet spriteSheet = new SpriteSheet("skier_sheet", "skier_sprite_sheet.png", 16, 16, (int) dimension.y, (int) dimension.x);
    private Map<String, Sprite> spriteMap = new HashMap<>();

    private GameSprites() {
        spriteMap.put("skier_default", spriteSheet.getSprite(0, 0));
        spriteMap.put("skier_turning", spriteSheet.getSprite(1, 0));
        spriteMap.put("pine_tree", spriteSheet.getSprite(2, 0));

    }

    public static GameSprites getInstance() {
        if (instance == null) {
            instance = new GameSprites();
        }
        return instance;
    }

    public Map<String, Sprite> getSpriteMap() {
        return spriteMap;
    }

    public Sprite getSprite(String spriteName) {
        return spriteMap.get(spriteName);
    }


}
