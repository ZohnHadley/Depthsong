package co.px.depthsong.layers.models.util;

import co.px.depthsong.gameUtils.GeneralTimer;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteSheetAnimation {


    private String title = "Animaiton_null";
    private SpriteSheet spriteSheet;
    private Sprite sprite = new Sprite();
    private int width = 0;
    private int height = 0;

    private int x_offset = 0;
    private int y_offset = 0;

    private int frameCount = 0;
    private float speed = 0.1f;

    private float current_frame_index = 0;
    private GeneralTimer frame_timer = new GeneralTimer();

    private boolean isFlippedHorizontaly = false;
    private boolean isFlippedVertically = false;

    public SpriteSheetAnimation(String _title, SpriteSheet _spriteSheet, int _x_offset, int _y_offset, int _frameCount, float _speed) {
        title = _title;
        spriteSheet = _spriteSheet;
        x_offset = _x_offset;
        y_offset = _y_offset;

        width = _spriteSheet.getWidth_of_sprites();
        height = _spriteSheet.getHeight_of_sprites();

        frameCount = _frameCount;
        speed = _speed;

        isFlippedHorizontaly = false;
        isFlippedVertically = false;
        sprite = new Sprite(spriteSheet.getTexture_sprite_sheet(), x_offset, y_offset, width, height);


    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public float getSpeed() {
        return speed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void animate(float delta) {

        if (current_frame_index == frameCount) {
            frame_timer = new GeneralTimer();
            current_frame_index = 0;
        }

        if (frame_timer.secondsHasPassed(speed)) {
            frame_timer = new GeneralTimer();
            int x_clacl = (int)(x_offset + (current_frame_index * width));
            sprite.setRegion(x_clacl, y_offset, width, height);
            current_frame_index += 1;
        }
    }
}
