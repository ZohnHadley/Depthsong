package co.px.depthsong.enginUtils;

import co.px.depthsong.engineCore.models.util.SpriteSheet;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;


public class GameSprites {

    private static GameSprites instance;
    public final Vector2 dimension = new Vector2(16, 16);
    private SpriteSheet spriteSheet = new SpriteSheet("nethackSpriteSheet", "Modern_tiles.png", 27, 40, (int) dimension.y, (int) dimension.x);
    private Map<String, Sprite> spriteMap = new HashMap<>();

    private GameSprites() {
        spriteMap.put("question_mark", spriteSheet.getSprite(8, 25));

        spriteMap.put("grave_stone", spriteSheet.getSprite(16, 21));

        spriteMap.put("viking", spriteSheet.getSprite(25, 8));
        spriteMap.put("mage", spriteSheet.getSprite(29, 8));

        spriteMap.put("brown_ant", spriteSheet.getSprite(0, 0));
        spriteMap.put("wasp_drone", spriteSheet.getSprite(2, 0));

        spriteMap.put("black_ant", spriteSheet.getSprite(1, 0));

        spriteMap.put("spawn_point", spriteSheet.getSprite(39, 26));
        spriteMap.put("wall", spriteSheet.getSprite(0, 21));
        spriteMap.put("floor", spriteSheet.getSprite(10, 21));
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
